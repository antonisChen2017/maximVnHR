SELECT   '<toDay>'   as DAY , Count(distinct A.EmpCode) As EmpInCount, D.DEPARTMENT,V.UNIT  
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate= '<toDay>'
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<toDay>'))
AND (A.WorkFTime='00:00' AND A.WorkETime='00:00')
Group By D.DEPARTMENT,V.UNIT