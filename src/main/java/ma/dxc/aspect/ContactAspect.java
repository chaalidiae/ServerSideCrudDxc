package ma.dxc.aspect;

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

import static ma.dxc.service.audit.Operation.INSERTE_CONTACT;
import static ma.dxc.service.audit.Operation.UPDATE_CONTACT;
import static ma.dxc.service.audit.Operation.DELETE_CONTACT;

import ma.dxc.model.Audit;
import ma.dxc.model.Contact;
import ma.dxc.repository.AuditRepository;
import ma.dxc.service.ContactServiceImpl;
import ma.dxc.service.audit.Operation;



@Aspect
@Component
public class ContactAspect {
	
	@Autowired
    private Javers javers;
	
	@Autowired
	AuditRepository auditRepository;
	
	@Autowired
    private ContactServiceImpl service;


	
	@Pointcut(value = "execution(* ma.dxc.service.ContactServiceImpl.save(..))")
    public void mySavePointcut(){ }
	
	@Pointcut(value = "execution(* ma.dxc.service.ContactServiceImpl.update(..)) && args(id,contact,..)")
    public void myUpdatePointcut(Long id, Contact contact){ }
	
	@Pointcut(value = "execution(* ma.dxc.service.ContactServiceImpl.delete(..))")
    public void myDeletePointcut(){ }
	
	@AfterReturning(pointcut = "mySavePointcut()",returning= "result")
    public void logAfterReturningUsers(JoinPoint joinPoint, Object result) throws Throwable{
		Contact contact = (Contact) result;
		Long objectID = contact.getId();
    	String objectType = contact.getClass().getName();
		Date date = new Date();
    	String changes = contact.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,INSERTE_CONTACT,changes);
    }
	
	@Around("myUpdatePointcut(id,contact)")
    public Object applicationLogger (ProceedingJoinPoint proceedingJoinPoint, Long id, Contact contact) throws Throwable {
		
		
		Long objectID = id;
    	String objectType = contact.getClass().getName();
		Date date = new Date();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	
    	Contact contactAudited = service.findOne(id); 
    	System.out.println("**************\n"+contactAudited.getId()+"\n"+contactAudited.toString()+"\n*******************\n");
    	javers.commit(user,contactAudited);
    	
        Object object = proceedingJoinPoint.proceed();
        
        contactAudited = service.findOne(id);
    	javers.commit(user,contactAudited);
    	String changes = javers.findChanges( QueryBuilder.byInstance(contactAudited).build()).toString();
    	System.out.println("\n \n *************************** \n\n"+changes+"\n \n *************************** \n\n");

    	saveAudit(user, objectID, objectType, date, UPDATE_CONTACT, changes);
        
		
        return object;
    }
	
	@AfterReturning(pointcut = "myDeletePointcut()",returning= "result")
    public void logAfterReturningUser(JoinPoint joinPoint, Object result) throws Throwable{
		Contact contact = (Contact) result;
		Long objectID = contact.getId();
    	String objectType = contact.getClass().getName();
		Date date = new Date();
    	String changes = "DELETED : "+contact.toString();
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	String user = authentication.getName();
    	saveAudit(user, objectID, objectType, date,DELETE_CONTACT,changes);
    }
	
	public void saveAudit(String user,Long objectID,String objectType,Date date,Operation operation,String changes) {
		Audit audit = new Audit(user, objectID, objectType, date, operation, changes);
		auditRepository.save(audit);
	}
	
	
}
