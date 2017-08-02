Declare @FDate DateTime,@EDate DateTime
Set @FDate=Convert(DateTime,'<FDate/>')
Set @EDate=DateAdd(Day,-1,DateAdd(Month,1,Convert(DateTime,'<FDate/>')))

Declare @FFDate Char(10)
Set @FFDate=Convert(Char(10),@FDate,111)
Declare @i int
Set @i=0

While @i<=DateDiff(Day,@FDate,@EDate)
	Begin
	
DELETE FROM [hr].[dbo].[VN_DAY_REPORT]
WHERE DAY=@FFDate

INSERT INTO  [dbo].[VN_DAY_REPORT](
	[DAY] ,
	[EMPLOYEENO] ,	
	[EMPLOYEE] ,
	[DEPARTMENT] ,
	[UNIT] ,
	[ATTENDANCE] ,
	[OVERTIME] ,
    [HOLIDAYH] ,
	[HOLIDAYI] ,
	[HOLIDAYE] ,
	[HOLIDAYD] ,	
	[HOLIDAYF] ,
	[HOLIDAYB] ,
	[HOLIDAYA] ,	
	[HOLIDAYG] ,
	[HOLIDAYO] ,
	[HOLIDAYS] ,
	[NOTWORK] ,
	[BELATE] ,
	[EARLY] ,
	[STOPWORK] ,
	[MEALS] ,
	[NOTE] 
) 
SELECT  
    @FFDate,
	VE.EMPLOYEENO ,
	VE.EMPLOYEE ,
	VD.DEPARTMENT,
	VU.UNIT ,
	(
	  Select 
	  	case when (WorkFDate='' AND WorkEDate='' ) 
		then 
			'0'
		else  
		 case when (DATEDIFF (HOUR,WorkFDate,WorkEDate))>0  then 
		     case when (DATEDIFF (HOUR,WorkFDate,WorkEDate))>=8
		     then 
				'8'
				else
					(DATEDIFF (HOUR,WorkFDate,WorkEDate))
				end
			 else  (
			 select CAST((T.AllMin-T.DinnTime)/60 as decimal(2, 1)) AS TIME from PWERP_MS.dbo.RsKQResult R
JOIN PWERP_MS.dbo.RsBasTurn T
ON R.Turn=T.Code
JOIN HR_EMPLOYEE E
ON E.EmpCode=R.EmpCode
where E.EMPLOYEENO=VE.EMPLOYEENO
AND FDate=@FFDate)
			end
	  end 
	    FROM  
	 PWERP_MS.dbo.RsKQResult AS K
	 ,HR_EMPLOYEE SVE
	 where
	 SVE.EmpCode=K.EmpCode
	 AND FDate= @FFDate
	 and SVE.EMPLOYEENO=VE.EMPLOYEENO
	)
	AS ATTENDANCE,
	(
	select top 1 APPLICATION_HOURS from VN_OVERTIME_S
	where EP_ID=VE.ID
	AND LEAVEAPPLY IN ('1')
	AND NOT ((@FFDate+' 23:59:59' < OVERTIME_START) OR (@FFDate+' 00:00:00'> OVERTIME_END))
	)
	 AS OVERTIME,
	(
	select 
	case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS='H'
	)  AS HOLIDAYH, --年假
	(
	select  case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS='I'
	) AS HOLIDAYC,--工傷假
	(
    select  case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS='E'
	) AS HOLIDAYE,--產假
	(
    select  case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS='D'
	) AS HOLIDAYD,--婚假
	(
	 select  case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND   H.HOLIDAYCLAS='F'
	) AS HOLIDAYF,--喪假
	(
	select 
	case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND   H.HOLIDAYCLAS='B'
	) AS HOLIDAYB,--病假
	(
	select 
	case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS='A'
	) AS HOLIDAYA,--事假
	(
	select 
	case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT (('<FDate/> 23:59:59' < L.STARTLEAVEDATE) OR ('<FDate/> 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS IN ('G')
	) AS HOLIDAYG,--周六調休
	(
	select 
	case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS IN ('O')
	) AS HOLIDAYO,--公假
			(
	select 
	case 
	when datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)>='8' 
	then '8'
	else datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) end 
	from VN_LEAVECARD L,VN_LHOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < L.STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> L.ENDLEAVEDATE))
	AND  H.HOLIDAYCLAS IN ('S')
	) AS HOLIDAYO,--其他
	(
    select 
	case when (SK.WorkFDate='' AND SK.WorkEDate='' ) 
	then  (
			 select CAST((T.AllMin-T.DinnTime)/60 as decimal(2, 1)) AS TIME from PWERP_MS.dbo.RsKQResult R
JOIN PWERP_MS.dbo.RsBasTurn T
ON R.Turn=T.Code
JOIN HR_EMPLOYEE E
ON E.EmpCode=R.EmpCode
where E.EMPLOYEENO=VE.EMPLOYEENO
AND FDate=@FFDate)
	else  '0'
	end 
	from   PWERP_MS.dbo.RsKQResult SK,
	PWERP_MS.dbo.RsEmployee SE ,
    PWERP_MS.dbo.RsBasTurn SB,
    HR_EMPLOYEE SVE,
    VN_UNIT SVU,
    VN_DEPARTMENT SVD
	Where 1=1
	AND SK.EmpCode=SE.EmpCode
	AND SK.Turn= SB.Code
	AND SVE.EMPLOYEENO= SE.RsEmpCode
	AND SVU.ID= SVE.UNIT_ID
	AND SVD.ID= SVE.DEPARTMENT_ID
	AND FDate=@FFDate
	AND <SVEDEPARTMENT/>
	AND <SVEUNIT/>
	AND SVE.EMPLOYEENO=VE.EMPLOYEENO
	) AS NOTWORK,
	(
	select 
	convert(numeric(8,0),round(( SK.LTime),2))
	from   PWERP_MS.dbo.RsKQResult SK,
	PWERP_MS.dbo.RsEmployee SE ,
    PWERP_MS.dbo.RsBasTurn SB,
    HR_EMPLOYEE SVE,
    VN_UNIT SVU,
    VN_DEPARTMENT SVD
	Where 1=1
	AND SK.EmpCode=SE.EmpCode
	AND SK.Turn= SB.Code
	AND SVE.EMPLOYEENO= SE.RsEmpCode
	AND SVU.ID= SVE.UNIT_ID
	AND SVD.ID= SVE.DEPARTMENT_ID
	AND FDate=@FFDate
	AND <SVEDEPARTMENT/>
	AND <SVEUNIT/>
	AND  1=1
	AND SVE.EMPLOYEENO=VE.EMPLOYEENO
	)
	AS BELATE,
	(
	select 
	convert(numeric(8,0),round(( SK.LeaveTime),2))
	from   PWERP_MS.dbo.RsKQResult SK,
	PWERP_MS.dbo.RsEmployee SE ,
    PWERP_MS.dbo.RsBasTurn SB,
    HR_EMPLOYEE SVE,
    VN_UNIT SVU,
    VN_DEPARTMENT SVD
	Where 1=1
	AND SK.EmpCode=SE.EmpCode
	AND SK.Turn= SB.Code
	AND SVE.EMPLOYEENO= SE.RsEmpCode
	AND SVU.ID= SVE.UNIT_ID
	AND SVD.ID= SVE.DEPARTMENT_ID
	AND FDate=@FFDate
	AND <SVEDEPARTMENT/>
	AND <SVEUNIT/>
	AND  1=1
	AND SVE.EMPLOYEENO=VE.EMPLOYEENO
	)
	AS EARLY,
	(
	select 
	case 
	when datediff(hh,K.STARTSTOPDATE,K.ENDENDDATE)>='8' 
	then '8'
	else datediff(hh,K.STARTSTOPDATE,K.ENDENDDATE) end 
	from VN_STOPWORKING K,VN_STOPWORKRESON S
	where EP_ID=VE.ID
	AND K.REASON_ID= S.ID
	AND NOT ((@FFDate+' 23:59:59' < K.STARTSTOPDATE) OR (@FFDate+' 00:00:00'> K.ENDENDDATE))
	) AS STOPWORK,
	'' AS MEALS,
	(
	select convert(nvarchar(max),isnull((
	select top 1 NOTE from VN_OVERTIME_S
	where EP_ID=VE.ID
	AND LEAVEAPPLY IN ('1')
	AND NOT ((@FFDate+' 23:59:59' < OVERTIME_START) OR (@FFDate+' 00:00:00'> OVERTIME_END))
	) ,''))+'  '+convert(nvarchar(max),isnull((
	select NOTE from VN_LEAVECARD
	where EP_ID=VE.ID
	AND LEAVEAPPLY IN ('1')--審核通過
	AND NOT ((@FFDate+' 23:59:59' < STARTLEAVEDATE) OR (@FFDate+' 00:00:00'> ENDLEAVEDATE))
	),'')) 
	) AS NOTE
	from   
	PWERP_MS.dbo.RsEmployee E ,
    HR_EMPLOYEE VE,
    VN_UNIT VU,
    VN_DEPARTMENT VD
	Where 1=1
	AND VE.EMPLOYEENO= E.RsEmpCode
	AND VU.ID= VE.UNIT_ID
	AND VD.ID= VE.DEPARTMENT_ID
	AND <VEDEPARTMENT/>
	AND <VEUNIT/>
	AND E.LeaveFlag='0' --在職
	
	Set @FFDate=Convert(Char(10),DateAdd(Day,@i+1,@FDate),111)
	Set @i=@i+1
End