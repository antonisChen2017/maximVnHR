SELECT distinct D.ID,D.DEPARTMENT
  FROM [hr].[dbo].[VN_DEPT_LEAVE_ROLE] L
   JOIN VN_DEPARTMENT AS D On D.ID=L.dept
  where (SINGROLEL0EP='<EMPLOYEENO/>' 
  or SINGROLEL1EP='<EMPLOYEENO/>' 
  or SINGROLEL2EP='<EMPLOYEENO/>' 
  or SINGROLEL3EP='<EMPLOYEENO/>'  
  or SINGROLEL4EP='<EMPLOYEENO/>')