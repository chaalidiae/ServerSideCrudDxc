package ma.dxc.aspect;

import static ma.dxc.service.audit.Operation.DELETE_USER;
import static ma.dxc.service.audit.Operation.INSERTE_USER;
import static ma.dxc.service.audit.Operation.UPDATE_USER;

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

import ma.dxc.model.AppUser;
import ma.dxc.model.Audit;
import ma.dxc.repository.AuditRepository;
import ma.dxc.service.UserServiceImpl;
import ma.dxc.service.audit.Operation;

@Aspect
@Component
public class UserAspect {
	
	@Autowired
    private Javers javers;
	
	@Autowired
	AuditRepository auditRepository;
	
	@Autowired
	UserServiceImpl service;
	
	@Pointcut(value = "execution(* ma.dxc.service.UserServiceImpl.save(..))")
    public void mySavePointcut(){ }
	
	@Pointcut(value = "execution(* ma.dxc.service.UserServiceImpl.update(..)) && args(id,appUser,..)")
    public void myUpdatePointcut(Long id, AppUser appUser){ }

	@Pointcut(value = "execution(* ma.dxc.service.UserServiceImpl.delete(..))")
    public void myDeletePointcut(){ }
	
	@AfterReturning(pointcut = "mySavePointcut()",returning= "result")
    public void logAfterReturningUsers(JoinPoint joinPoint, Object result) throws Throwable{
		AppUser appUser = (AppUser) result;
		Long objectID = appUser.getId();
    	String objectType = appUser.getClass().getName();
		Date date = new Date();
    	//String changes = appUser.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,INSERTE_USER,"");
    }
	
	@Around("myUpdatePointcut(id,appUser)")
    public Object applicationLogger (ProceedingJoinPoint proceedingJoinPoint, Long id, AppUser appUser) throws Throwable {
        
		Long objectID = id;
    	String objectType = appUser.getClass().getName();
		Date date = new Date();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	AppUser appUserAudited = service.findOne(id);    	
    	javers.commit(user,appUserAudited);
        Object object = proceedingJoinPoint.proceed();
        
        appUserAudited = service.findOne(id);
    	javers.commit(user,appUserAudited);
    	String changes = javers.findChanges( QueryBuilder.byInstance(appUserAudited).build()).toString();
    	saveAudit(user, objectID, objectType, date, UPDATE_USER, symplifyChanges(changes));
        
		
        return object;
    }
	
	@AfterReturning(pointcut = "myDeletePointcut()",returning= "result")
    public void logAfterReturningUser(JoinPoint joinPoint, Object result) throws Throwable{
		AppUser appUser = (AppUser) result;
		Long objectID = appUser.getId();
    	String objectType = appUser.getClass().getName();
		Date date = new Date();
    	//String changes = "DELETED : "+contact.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,DELETE_USER,"");
    }
	
	public void saveAudit(String user,Long objectID,String objectType,Date date,Operation operation,String changes) {
		Audit audit = new Audit(user, objectID, objectType, date, operation, changes);
		auditRepository.save(audit);
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
