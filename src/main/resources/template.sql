#namespace("fault")
    #sql("findList")
     select * from ht_im_fault_form 
        #for(x:cond)
         	#(for.index==0? "where" : "and")
	        #if(x.key=="repair_name" || x.key=="responsible_dep" || x.key=="report_name" )
	        	#(x.key) like concat('%',#para(x.value), '%')	
			#else
			 	#(x.key) = #para(x.value)
			#end
	    #end
      ORDER BY repair_time DESC
    #end
    
    #sql("findCommentList")
    	select f.id ,f.order_no, f.describe, f.location, f.type, f.repair_name, c.`level`,c.content,c.time
		from ht_im_order_take t JOIN ht_im_fault_form f ON t.fault_id = f.id JOIN ht_im_order_comment c ON t.fault_id = c.fault_id 
		#for(x:cond)
			#(for.index==0? "where" : "and")
			#if(x.key=="repair_name")
	        	#(x.key) like concat('%',#para(x.value), '%')	
			#else
			 	#(x.key) = #para(x.value)
			#end
		#end
	#end	
#end
