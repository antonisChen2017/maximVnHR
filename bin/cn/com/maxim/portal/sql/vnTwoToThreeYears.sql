SELECT Count(distinct A.EmpCode) as yco
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE  A.FDate='<toDay>'
AND B.Nation='0002' 
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<toDay>'))
and datediff(yy,InDate,GETDATE())=3