SELECT  '<toDay>'   as DAY , Count(distinct A.EmpCode) As EmpInCount, D.DEPARTMENT,V.UNIT  
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_OVERTIME_S S On  S.EP_ID=E.ID
 LEFT JOIN hr.dbo.VN_OVERTIME_M M On  S.M_ID=M.ID
WHERE A.FDate= '<toDay>' 
and S.STATUS in ('I')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>= '<toDay>'))
AND S.OVERTIME_START>= '<toDay>  00:00:00'
AND S.OVERTIME_end<='<toDay> 23:59:59'
Group By D.DEPARTMENT,V.UNIT