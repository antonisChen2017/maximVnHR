select  COUNT(*) AS  yco
from PWERP_MS.dbo.RsEmployee B 
WHERE B.Nation='0002'
and B.InDate<'<LASTYEAR/>'
and B.InDate>= '<TWOYEAR/>'
AND LeaveFlag='0'