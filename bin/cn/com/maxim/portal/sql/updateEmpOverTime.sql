UPDATE [hr].[dbo].[VN_OVERTIME_S]
   SET 
      [APPLICATION_HOURS] ='<APPLICATION_HOURS/>'
      ,[OVERTIME_START] ='<OVERTIME_START/>'
      ,[OVERTIME_END] ='<OVERTIME_END/>'
      ,[REASONS] ='<REASONS/>'
      ,[UNIT] ='<UNIT/>'
      ,[SUBMITTIME] = getdate()
      ,[NOTE]  ='<NOTE/>'
      ,[USERREASONS] ='<USERREASONS/>'
      ,[TURN] = '<TURN/>'
 WHERE  id='<rowID>'