UPDATE [hr].[dbo].[VN_LEAVECARD]
   SET 
       [HD_ID]= '<HD_ID/>'
      ,[STARTLEAVEDATE] = '<STARTLEAVEDATE/>'
      ,[ENDLEAVEDATE] = '<ENDLEAVEDATE/>'
      ,[AGENT] = '<AGENT/>'
      ,[DAYCOUNT] = '<DAYCOUNT/>'
      ,[HOURCOUNT] = '<HOURCOUNT/>'
      ,[MINUTECOUNT] = '<MINUTECOUNT/>'
      ,[NOTE] = '<NOTE/>'
      ,[SUBMITTIME] =  getdate()
 WHERE ID='<rowID/>'