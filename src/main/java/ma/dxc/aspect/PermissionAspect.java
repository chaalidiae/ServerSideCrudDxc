package ma.dxc.aspect;

import static ma.dxc.service.audit.Operation.INSERTE_PERMISSION;
import static ma.dxc.service.audit.Operation.UPDATE_PERMISSION;

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

import ma.dxc.model.Audit;
import ma.dxc.model.Permission;
import ma.dxc.repository.AuditRepository;
import ma.dxc.service.PermissionServiceImpl;
import ma.dxc.service.audit.Operation;

@Aspect
@Component
public class PermissionAspect {
	
	@Autowired
    private Javers javers;
	
	@Autowired
	AuditRepository auditRepository;
	
	@Autowired
	PermissionServiceImpl service;
	
	@Pointcut(value = "execution(* ma.dxc.service.PermissionServiceImpl.save(..))")
    public void mySavePointcut(){ }
	
	@Pointcut(value = "execution(* ma.dxc.service.PermissionServiceImpl.update(..)) && args(id,permission,..)")
    public void myUpdatePointcut(Long id, Permission permission){ }
	
	@AfterReturning(pointcut = "mySavePointcut()",returning= "result")
    public void logAfterReturningUsers(JoinPoint joinPoint, Object result) throws Throwable{
		Permission permission = (Permission) result;
		Long objectID = permission.getId();
    	String objectType = permission.getClass().getName();
		Date date = new Date();
    	String changes = permission.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,INSERTE_PERMISSION,changes);
    }
	
	@Around("myUpdatePointcut(id,permission)")
    public Object applicationLogger (ProceedingJoinPoint proceedingJoinPoint, Long id, Permission permission) throws Throwable {
        
		Long objectID = id;
    	String objectType = permission.getClass().getName();
		Date date = new Date();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	
    	Permission permissionAudited = service.findOne(id);   	
    	javers.commit(user,permissionAudited);
    	
        Object object = proceedingJoinPoint.proceed();
        
        permissionAudited = service.findOne(id);
    	javers.commit(user,permissionAudited);
    	String changes = javers.findChanges( QueryBuilder.byInstance(permissionAudited).build()).toString();
    	saveAudit(user, objectID, objectType, date, UPDATE_PERMISSION, changes);
        
		
        return object;
    }
	
	public void saveAudit(String user,Long objectID,String objectType,Date date,Operation operation,String changes) {
		Audit audit = new Audit(user, objectID, objectType, date, operation, changes);
		auditRepository.save(audit);
	}
}
