SELECT  '<toDay>'  as DAY ,D.DEPARTMENT,V.UNIT,Count(distinct A.EmpCode) As EmpInCount
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.VN_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
WHERE A.FDate= '<toDay>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>= '<toDay>'))
and A.Turn='C2'
AND (A.WorkFTime<>'00:00' OR A.WorkETime<>'00:00')
Group By D.DEPARTMENT,V.UNIT