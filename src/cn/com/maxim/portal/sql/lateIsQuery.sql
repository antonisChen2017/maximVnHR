SELECT COUNT(*) AS LATE
   FROM [hr].[dbo].[VN_ISQUERY_LATE]
  WHERE YEAR='<year/>'  AND  MONTH='<Month/>'   AND ISLATE='<isLate/>'