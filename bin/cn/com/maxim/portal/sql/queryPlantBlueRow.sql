SELECT 
 IDENTITY(INT,1,1) as ROW
      ,[SORT]
     INTO tmpTableName 
  FROM [hr].[dbo].[VN_YEAR_MONTH_PLANT_ATTENDANCE] P
WHERE ymd='<YMD/>'
  order by P.DEPARTMENT,SORT
  
SELECT * FROM tmpTableName;
DROP TABLE tmpTableName;