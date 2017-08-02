UPDATE VN_EMPLOYEE_UNIT
SET ROLE='E'
WHERE ROLE IS NULL

select  d.DEPARTMENT as 部门 ,vu.UNIT AS 单位 ,v.EMPLOYEE as 名称 ,
v.EMPLOYEENO as 员工编号 ,v.ENTRYDATE as 到职日 ,v.DUTIES as 职称
,re.TITLE as 权限
--,R.LeaveDate as 離職日
,(case when R.LeaveFlag=1 then '离职' else '在职' end ) as 是否離職
--R.LeaveFlag as 是否離職
from HR_EMPLOYEE as v
LEFT OUTER JOIN VN_DEPARTMENT as d ON v.DEPARTMENT_ID = d.ID
LEFT OUTER JOIN VN_UNIT as vu ON v.UNIT_ID = vu.ID
LEFT OUTER JOIN VN_EMPLOYEE_UNIT as ut ON  v.EMPLOYEENO=ut.EMPLOYEENO 
LEFT OUTER JOIN VN_ROLE as re ON v.ROLE=re.ID
INNER JOIN PWERP_MS.dbo.RsEmployee R ON v.EmpCode=R.EmpCode
WHERE  v.DEPARTMENT_ID='<DEPTID/>'
AND <UNIT/>

