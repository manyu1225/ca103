<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

 <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
   	
    
    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>

<title>Insert title here</title>
</head>
<body>
	
		<ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
		  <li class="nav-item">
		    <a class="nav-link active" id="pills-home-tab" data-toggle="pill" href="#pills-home" role="tab" aria-controls="pills-home" aria-selected="true">Home</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" id="pills-profile-tab" data-toggle="pill" href="#pills-profile" role="tab" aria-controls="pills-profile" aria-selected="false">Profile</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" id="pills-contact-tab" data-toggle="pill" href="#pills-contact" role="tab" aria-controls="pills-contact" aria-selected="false">Contact</a>
		  </li>
		</ul>
		<div class="tab-content" id="pills-tabContent">
		  <div class="tab-pane fade show active" id="pills-home" role="tabpanel" aria-labelledby="pills-home-tab">
		  	<div id="test"></div>
		  
		  </div>
		  <div class="tab-pane fade" id="pills-profile" role="tabpanel" aria-labelledby="pills-profile-tab">...</div>
		  <div class="tab-pane fade" id="pills-contact" role="tabpanel" aria-labelledby="pills-contact-tab">...</div>
		</div>
	
	<script>
		$(function(){
			$('#test').append("fewfew<div id='d0'><br></div><img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEwAACxMBAJqcGAAABRhJREFUWIXtln1MlVUcxz/n3IfLVby8ibzJiyxfbvcKiE58AURQQPMFEstZaatZba7W1uqftjKtrdZqztVWOeeWq2zZmro2XZkGKoIgL+IbuYmAyhWuKBpcvTw8pz940ZB7kVv/1e+fZ8/5/Z7f93POc36/c+B/+6+bGE1wdna25tFCCiSqCCXmGopgKVEYqhspLwqh9roJ/OnUoT2d/zrAvJyVC4TBLqGJRF9xhlJ3BWJTWdaMT9i82Rgpr+lRxOfnFOVLaRwUUoaPFCuE0IQgL77ZGdHS2HDgHwNkZi4LMzRxRAhpfRTYQRBIT0yy1TY3NlzwFSdHSqQCtVelEBNGIz5gvYIPGOE3jwgArPdHvD+5IzOnaKbfABmLlycAk735Y6IjKchb6BNCmYw8X37N59eGKXW4BbQEBlK4vIAX1q0hKGgs6bPS2LlrN1evOYchEKn+A6ASHvyF9senkr8om4JF2Vit4wbHCxZnk5ebRUVlDQd+OczRsgp6evS+ORgqzm8ABaED8gsy57LxpecB6Lx9h87bd4iKjCAgIADn9XZ0XSc+LpaXX3yOJwuX8vqb72IYBlIQ6jeAEEKazQGkpSTjcnWw4+vvsE+b2g+nOFlVS3hYKMHWcURFTuifsUFFVTXJDhvONhfXW50+S913FRi4J8ZGs+6ZYmJiI0mflYZt2mTaXC7WFK9kUkIcyQ4bS/JziImJxNPj4alVy5mUEM/smanMnpmCktLt9wogjXZd1+nudmMNGke7y8XS/BySHTZ6dB0hBRHjw7l5s5Os+XPImJeO09lGgDkAa7CVlqutCJTLbwDDoKXd1YGmaViDgggLC2XbFzuRUiCFoHBZAU0tV6g/e57Kmjo8Hg9jAi1kzZ+DUopDh0sxoMVvACVl+92799i+81smJcbxyoZ1KKUAEKJve56sqqG4aBmhIcEYSiGF4ER5FT/s/Zmz5xoQ4P8KSEN0IxUX/riI2az9TXjAVhU+MTgm+59nGy5SWVXbF4/o8htAM/XIXtUXcqmxmVuddwgNsVJXfw63+y5TJidhDgjgWut1Ll1uIi83G00zcbr+3GAOpZTPje7TaeimweP3z64utn72FQCpyXZmpNhpbGympraekBArS/Nz0TQTZRWnOHu+YTCHQPg8wn0CKBOOB9+PllXw/Y/7ALBYLMTHxxIXP5HI/h5w5Wor2z7fzr17nvs5FHZfGj6PyozcFQcRsuDBMXOgmay5s3lt4wYixocBoOu97N6zl737D3C9fcieMwyPMomkskP7r40KIGNR4XvAJm9+u20q0x029N4eOm7c4vejJ7zPRFGl9ZJXUrLv1lCX902oGDuAp2ka0+02ptunEh4WxuWmFqKjIln/7GoAjpSUYbEEMm3KYxiGwtnWRuWpOi439bUAQxlRJQtn3qZk30MyXgF0jE9NyHXhYSHRa1cXsfbposFyqzxVS1dXN93dbixjLIBicc4C5qbfv3t4PB62fLiVkmPlCCnfepQL6kO2aElx2pc7vnGrIebxeNSvR0pV6fFyVXqsXB0pPa7aXTeGhqlew1BvvL2ldNTCA/bO+x9/5HY/pK+UUkrXdVVTd0ZVVdcpbzFKKXW6/kxjZu6KFG8aPhtRUmJCmsViGdZnMpmYkeIY1vegTXfYJyUmJGYeg9PD+X32AWuwNWpEhRFMCEF0zASvzcjnClRXn/6tqanpBIBScrBkZX/TF0IJIeSwpSzE/cl1dNxq9qbxF3HiFxg2WwIwAAAAAElFTkSuQmCC' style='width:200px;height:200px;''><div id='d1'><br>fewfewfewf</div>");
		});
	
	
	</script>
</body>

</html>