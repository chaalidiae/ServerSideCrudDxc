package ma.dxc.aspect;


import java.util.ArrayList;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.javers.core.Javers;
import org.javers.repository.jql.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ma.dxc.model.AppRole;
import ma.dxc.model.Audit;
import ma.dxc.repository.AuditRepository;
import ma.dxc.service.RoleServiceImpl;
import ma.dxc.service.audit.Operation;

import static ma.dxc.service.audit.Operation.DELETE_ROLE;
import static ma.dxc.service.audit.Operation.INSERTE_ROLE;
import static ma.dxc.service.audit.Operation.UPDATE_ROLE;;

@Aspect
@Component
public class RoleAspect {

	@Autowired
    private Javers javers;
	
	@Autowired
	AuditRepository auditRepository;
	
	@Autowired
	RoleServiceImpl service;
	
	@Pointcut(value = "execution(* ma.dxc.service.RoleServiceImpl.save(..))")
    public void mySavePointcut(){ }
	
	@Pointcut(value = "execution(* ma.dxc.service.RoleServiceImpl.update(..)) && args(id,appRole,..)")
    public void myUpdatePointcut(Long id, AppRole appRole){ }
	
	@Pointcut(value = "execution(* ma.dxc.service.RoleServiceImpl.delete(..))")
    public void myDeletePointcut(){ }
	
	@AfterReturning(pointcut = "mySavePointcut()",returning= "result")
    public void logAfterReturningUsers(JoinPoint joinPoint, Object result) throws Throwable{
		AppRole appRole = (AppRole) result;
		Long objectID = appRole.getId();
    	String objectType = appRole.getClass().getName();
		Date date = new Date();
    	String changes = appRole.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,INSERTE_ROLE,"");
    }
	
	@Around("myUpdatePointcut(id,appRole)")
    public Object applicationLogger (ProceedingJoinPoint proceedingJoinPoint, Long id, AppRole appRole) throws Throwable {
        
		Long objectID = id;
    	String objectType = appRole.getClass().getName();
		Date date = new Date();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	AppRole appRoleAudited = service.findOne(id);    	
    	javers.commit(user,appRoleAudited);
		
        Object object = proceedingJoinPoint.proceed();
        
        appRoleAudited = service.findOne(id);
    	javers.commit(user,appRoleAudited);
    	String changes = javers.findChanges( QueryBuilder.byInstance(appRoleAudited).build()).toString();
    	saveAudit(user, objectID, objectType, date, UPDATE_ROLE, symplifyChanges(changes));
        
		
        return object;
    }
	
	public void saveAudit(String user,Long objectID,String objectType,Date date,Operation operation,String changes) {
		Audit audit = new Audit(user, objectID, objectType, date, operation, changes);
		auditRepository.save(audit);
	}
	
	@AfterReturning(pointcut = "myDeletePointcut()",returning= "result")
    public void logAfterReturningUser(JoinPoint joinPoint, Object result) throws Throwable{
		AppRole appRole = (AppRole) result;
		Long objectID = appRole.getId();
    	String objectType = appRole.getClass().getName();
		Date date = new Date();
    	//String changes = "DELETED : "+contact.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,DELETE_ROLE,"");
    }
	
	public String symplifyChanges(String changes) {
		String[] tab = changes.split("-");
		ArrayList<String> array = new ArrayList<String>();
		for (String string : tab) {
			array.add(string);
		}
		array.remove(0);
		String changement = "";
		for (String string : array) {
			String[] table = string.split(" value changed from ");
			String colomn = table[0].replace("'", "");
			String change = table[1].replace("'", "");
			String[] table2 = change.split("to");
			String oldValue = table2[0];
			String newValue = table2[1];
			//System.out.println("colomn:"+colomn+", oldValue = "+oldValue+", newValue = "+newValue );
			changement = changement +" column:"+colomn+", oldValue = "+oldValue+", newValue = "+newValue+"////";
		}
		return changement;
	}
}
