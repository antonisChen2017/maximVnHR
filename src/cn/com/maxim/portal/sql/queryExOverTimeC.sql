select 
 DT.DEPARTMENT 
 ,DT.ID AS DID
,UT.UNIT 
,UT.ID  AS UID
,EP.EMPLOYEENO  
,EP.EMPLOYEE  
,S.EP_ID
	from VN_OVERTIME_M AS M  
	INNER JOIN 
	VN_OVERTIME_S AS S 
	ON M.ID = S.M_ID 
	INNER JOIN 
	VN_DEPARTMENT AS DT 
	 ON M.DEPARTMENT = DT.ID 
	 INNER JOIN 
	 VN_UNIT AS UT 
	ON S.UNIT = UT.ID 
  INNER JOIN 
  HR_EMPLOYEE AS EP 
	ON S.EP_ID = EP.ID 
	 INNER JOIN 
  VN_ROLE AS RE 
	ON EP.ROLE= RE.ID 
	 where 1=1 
	and DATEPART(yyyy,OVERTIME_START) ='<YEAR/>' 
    and DATEPART ( mm ,OVERTIME_START )='<MONTH/>' 
    and EP.EMPLOYEENO='<EMPLOYEENO/>'
group by EP.EMPLOYEENO,EP.EMPLOYEE,DT.DEPARTMENT,DT.ID,UT.UNIT,RE.TITLE,S.EP_ID,UT.ID
HAVING sum(convert(float,APPLICATION_HOURS))>= (select VALUE from VN_CONFIG where [key]='CTIME')