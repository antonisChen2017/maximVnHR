SELECT     
	   EMPLOYEENO as '工号'
      ,[EMPLOYEE] as '姓名'
      ,d.DEPARTMENT as '部门'
      ,u.UNIT as '单位'
      ,[DUTIES] as '职称'
      ,r.TITLE as '角色'
      ,[Email]
       ,h.ID as 'action'
       , h.ID
  FROM [hr].[dbo].[HR_EMPLOYEE] as h,
  VN_UNIT as u ,
  VN_DEPARTMENT as d,
  VN_ROLE as r
  where u.ID=h.UNIT_ID 
  and d.ID=h.DEPARTMENT_ID
  and r.ID=h.ROLE
  and Romance ='1'
    <ROLE/>
  ORDER BY EMAIL DESC