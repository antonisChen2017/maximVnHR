INSERT INTO [hr].[dbo].[VN_LEAVECARD]
           ([EP_ID]
           ,[HD_ID]
           ,[APPLICATIONDATE]
           ,[STARTLEAVEDATE]
           ,[ENDLEAVEDATE]
           ,[AGENT]
           ,[DAYCOUNT]
           ,[HOURCOUNT]
           ,[MINUTECOUNT]
           ,[NOTE]
           ,[STATUS]
           ,[SAVETIME]
           ,[PROCESS]
           ,[SINGROLEL1]
           ,[SINGROLEL2]
           ,[SINGROLEL3]
           ,[SINGROLEL4]
           ,[SINGROLEL1EP]
           ,[SINGROLEL2EP]
           ,[SINGROLEL3EP]
           ,[SINGROLEL4EP]
           ,[LEAVEAPPLY])
     VALUES
           ('<EP_ID/>'
           ,'<HD_ID/>'
           ,'<APPLICATIONDATE/>'
           ,'<STARTLEAVEDATE/>'
           ,'<ENDLEAVEDATE/>'
           ,'<AGENT/>'
           ,'<DAYCOUNT/>'
           ,'<HOURCOUNT/>'
           ,'<MINUTECOUNT/>'
           ,'<NOTE/>'
           ,'<STATUS/>'
           ,getdate()
           ,'<PROCESS/>'
           ,'<SINGROLEL1/>'
           ,'<SINGROLEL2/>'
           ,'<SINGROLEL3/>'
           ,'<SINGROLEL4/>'
           ,'<SINGROLEL1EP/>'
           ,'<SINGROLEL2EP/>'
           ,'<SINGROLEL3EP/>'
           ,'<SINGROLEL4EP/>'
           ,'0')