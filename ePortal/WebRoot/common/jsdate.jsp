<%@ page contentType="text/html; charset=gbk"%>
<script language="javascript"> 
	function PageDate(){   
		currentDate = new Date();  
		with(currentDate){   
			day=getDay();
			month=getMonth()+1;
			this.document.write(getFullYear()+'��'+month+'��'+getDate()+'��')   
		}   
		if(day==1){document.write(' ����һ');}
		if(day==2){document.write(' ���ڶ�');}
		if(day==3){document.write(' ������');}
		if(day==4){document.write(' ������');}
		if(day==5){document.write(' ������');}
		if(day==6){document.write(' ������');}
		if(day==0){document.write(' ������');} 
	}
	PageDate();
</script>