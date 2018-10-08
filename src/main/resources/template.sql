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
#end
