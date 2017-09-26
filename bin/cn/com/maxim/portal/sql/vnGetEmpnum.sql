DELETE 
  FROM [hr].[dbo].[VN_EMPNUM]
  WHERE YMDKEY='<YMD>'

SELECT COUNT(YMDKEY) as count
  FROM [hr].[dbo].[VN_EMPNUM]
where YMDKEY='<YMD>'