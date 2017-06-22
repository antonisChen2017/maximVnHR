select
(
case when 
CONVERT(decimal(2,1),DAYCOUNT)>=3 then '1'
else '0' end
) as count
  FROM [hr].[dbo].[VN_LEAVECARD]
  where ID='<rowID/>'