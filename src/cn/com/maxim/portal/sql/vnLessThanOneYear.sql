select  COUNT(*) AS  yco
from PWERP_MS.dbo.RsEmployee B 
WHERE B.Nation='0002'
and B.InDate>='<LASTYEAR/>'
AND LeaveFlag='0'