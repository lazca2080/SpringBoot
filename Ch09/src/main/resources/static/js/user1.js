$(function(){
	$('.user1.list1').click(function(){
					
		$.ajax({
			url:'/Ch09/user1',
			method:'get',
			dataType:'json',
			success: function(data){
				console.log(data)
			},
		});
	});
	
	$('.user1.list2').click(function(){
		
		let uid = 'a101';
		
		$.ajax({
			url:'/Ch09/user1/'+uid,
			method:'get',
			dataType:'json',
			success: function(data){
				console.log(data)
			}
		});
	});
	
	$('.user1.register').click(function(){
		
		let jsonData = {
			"uid":"p101",	
			"name":"박길동",	
			"hp":"010-1234-5421",	
			"age":19,	
		};
		
		$.ajax({
			url:'/Ch09/user1',
			method:'post',
			data:jsonData,
			dataType:'json',
			success: function(data){
				console.log(data)
			}
		});
	});
	
	$('.user1.modify').click(function(){
		
		let jsonData = {
				"uid":"A777",	
				"name":"박길동",	
				"hp":"010-1234-2220",	
				"age":29,	
			};
			
		$.ajax({
			url:'/Ch09/user1',
			method:'PUT',
			data:jsonData,
			dataType:'json',
			success: function(data){
				console.log(data)
			}
		});
	});
	
	$('.user1.delete').click(function(){
		
		let uid = 'p101';
		
		$.ajax({
			url:'/Ch09/user1/'+uid,
			method:'DELETE',
			dataType:'json',
			success: function(data){
				console.log(data)
			}
		});
	});
});