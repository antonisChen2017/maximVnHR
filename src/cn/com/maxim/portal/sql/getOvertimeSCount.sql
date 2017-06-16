select count(EP_ID) as count
FROM [VN_OVERTIME_S]
WHERE OVERTIME_START <='<OTS/>'
 and OVERTIME_END>='<OTE/>'
 and EP_ID='<EMPID/>'