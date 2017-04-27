SELECT  
	--ROW_NUMBER() over(order by TotalTime) as 序號,
	VE.EMPLOYEENO AS 工號,
	VE.EMPLOYEE AS 姓名,
	VD.DEPARTMENT AS 部門,
	VU.UNIT 單位,
	(
	select TotalTime 
	from   PWERP_MS.dbo.RsKQResult SK,
	PWERP_MS.dbo.RsEmployee SE ,
    PWERP_MS.dbo.RsBasTurn SB,
    VN_EMPLOYEE SVE,
    VN_UNIT SVU,
    VN_DEPARTMENT SVD
	Where 1=1
	AND SK.EmpCode=SE.EmpCode
	AND SK.Turn= SB.Code
	AND SVE.EMPLOYEENO= SE.EmpID
	AND SVU.ID= SVE.UNIT_ID
	AND SVD.ID= SVE.DEPARTMENT_ID
	AND FDate='<FDate/>'
	AND SVE.DEPARTMENT_ID='<DEPARTMENT/>'
	AND SVE.UNIT_ID='<UNIT/>'
	AND Code NOT in ('C3','CD','CD1')
	AND SVE.EMPLOYEENO=VE.EMPLOYEENO
	)
	AS 早班時數,
	(
	select TotalTime 
	from   PWERP_MS.dbo.RsKQResult SK,
	PWERP_MS.dbo.RsEmployee SE ,
    PWERP_MS.dbo.RsBasTurn SB,
    VN_EMPLOYEE SVE,
    VN_UNIT SVU,
    VN_DEPARTMENT SVD
	Where 1=1
	AND SK.EmpCode=SE.EmpCode
	AND SK.Turn= SB.Code
	AND SVE.EMPLOYEENO= SE.EmpID
	AND SVU.ID= SVE.UNIT_ID
	AND SVD.ID= SVE.DEPARTMENT_ID
	AND FDate='<FDate/>'
	AND SVE.DEPARTMENT_ID='<DEPARTMENT/>'
	AND SVE.UNIT_ID='<UNIT/>'
	AND Code  in ('C3','CD','CD1')
	AND SVE.EMPLOYEENO=VE.EMPLOYEENO
	) 
	AS 晚班時數,
	(
	select APPLICATION_HOURS from VN_OVERTIME_S
	where EP_ID=VE.ID
	AND STATUS='I'
	and  OVERTIME_START>='<FDate/> 00:00:00'
	AND OVERTIME_END<='<FDate/> 23:59:59'
	)
	 AS 加班時數,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE) from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='8'
	) AS 年假,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)  from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='2'
	) AS 病假,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)  from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='1'
	) AS 事假,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)  from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='3'
	) AS 公假,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)  from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='5'
	) AS 產假,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)  from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='6'
	) AS 喪假,
	(
	select datediff(hh,L.STARTLEAVEDATE,L.ENDLEAVEDATE)  from VN_LEAVECARD L,VN_HOLIDAY H
	where EP_ID=VE.ID
	AND H.ID=L.HD_ID
	AND STATUS IN ('L','M')--審核通過
	and  L.STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND L.ENDLEAVEDATE<='<FDate/> 23:59:59'
	AND  HD_ID='15'
	) AS 曠工,
	K.LTime AS 遲到,
	(
	select datediff(hh,K.STARTSTOPDATE,K.ENDENDDATE) from VN_STOPWORKING K,VN_STOPWORKRESON S
	where EP_ID=VE.ID
	AND K.REASON_ID= S.ID
	and  K.STARTSTOPDATE>='<FDate/> 00:00:00'
	AND K.ENDENDDATE<='<FDate/> 23:59:59'
	) AS 待工,
	'' AS 補貼餐費,
	(
	select convert(nvarchar(max),isnull((
	select NOTE from VN_OVERTIME_S
	where EP_ID=VE.ID
	AND STATUS='I'
	and  OVERTIME_START>='<FDate/> 00:00:00'
	AND OVERTIME_END<='<FDate/> 23:59:59' 
	) ,''))+'  '+convert(nvarchar(max),isnull((
	select NOTE from VN_LEAVECARD
	where EP_ID=VE.ID
	AND STATUS IN ('L','M')--審核通過
	and  STARTLEAVEDATE>='<FDate/> 00:00:00'
	AND ENDLEAVEDATE<='<FDate/> 23:59:59'
	),'')) 
	) AS 備註
	from   PWERP_MS.dbo.RsKQResult K,
	PWERP_MS.dbo.RsEmployee E ,
    PWERP_MS.dbo.RsBasTurn B,
    VN_EMPLOYEE VE,
    VN_UNIT VU,
    VN_DEPARTMENT VD
	Where 1=1
	AND K.EmpCode=E.EmpCode
	AND K.Turn= B.Code
	AND VE.EMPLOYEENO= E.EmpID
	AND VU.ID= VE.UNIT_ID
	AND VD.ID= VE.DEPARTMENT_ID
	AND FDate='<FDate/>'
	AND VE.DEPARTMENT_ID='<DEPARTMENT/>'
	AND VE.UNIT_ID='<UNIT/>'
	AND E.LeaveFlag='0' --在職