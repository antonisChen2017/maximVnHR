SELECT  '<toDay>'    as DAY , Count(distinct A.EmpCode) As EmpInCount, D.DEPARTMENT,V.UNIT  
FROM PWERP_MS.dbo.RsKQResult A  
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
 JOIN hr.dbo.HR_EMPLOYEE E On  E.EMPLOYEENO=B.EmpID
 JOIN hr.dbo.VN_UNIT V On  E.UNIT_ID=V.ID
 JOIN hr.dbo.VN_DEPARTMENT D On  E.DEPARTMENT_ID=D.ID
 LEFT JOIN hr.dbo.VN_LEAVECARD L On  L.EP_ID=E.ID
WHERE A.FDate=  '<toDay>' 
and L.HD_ID in ('<hdID>')
and L.STATUS in ('L','M')
and (LeaveFlag='0' Or (LeaveFlag='1' And LeaveDate>=  '<toDay>' ))
AND L.STARTLEAVEDATE>=  '<toDay>  00:00:00'
AND L.ENDLEAVEDATE<='<toDay> 23:59:59'
Group By D.DEPARTMENT,V.UNIT