SELECT 
[ID]
,ISNULL((
select DEPARTMENT from VN_DEPARTMENT
where  P.DEPARTMENT=ID
),'X') AS DEPARTMENT
      ,[UNIT]
      ,[ROW]
      ,[C1]
      ,[C2]
      ,[C3]
      ,[C4]
      ,[C5]
      ,[C6]
      ,[C7]
      ,[C8]
      ,[C9]
      ,[C10]
      ,[C11]
      ,[C12]
      ,[C13]
      ,[C14]
      ,[C15]
      ,[C16]
      ,[C17]
      ,[C18]
      ,[C19]
      ,[C20]
  FROM [hr].[dbo].[VN_YEAR_MONTH_PLANT_ATTENDANCE] P
  where YMD='<YMD/>'
  order by P.DEPARTMENT,SORT