SELECT COUNT(*) as yco
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.VN_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
WHERE  A.FDate='<toDay>'
AND B.Nation='0002' 
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>='<toDay>'))
and InDate>= (getdate()-365)