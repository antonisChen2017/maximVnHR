select COUNT(distinct HE.EMPLOYEENO) AS  yco
from HR_EMPLOYEE as HE
LEFT OUTER JOIN VN_DEPARTMENT as d ON HE.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as V ON HE.UNIT_ID = V.ID
JOIN PWERP_MS.dbo.RsKQResult A  On A.EmpCode=HE.EmpCode
 JOIN PWERP_MS.dbo.RsEmployee B On A.EmpCode=B.EmpCode 
WHERE B.Nation='0002'
and HE.ENTRYDATE>='<LASTYEAR/>'