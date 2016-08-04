<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.3.1/jquery.maskedinput.js" type="text/javascript"></script>

<style>
    .contest_description{
        margin-bottom:35px;
    }
    .contest_form_div{
        margin-top:20px;
        width:75%;
        margin-left:80px;
    }
    
    .large_field{
        display:inline-block;
        width:49%;
    }
    .small_field {
        width:32.5%;
        display:inline-block;
    }
    
    .full_field input, .large_field input, .small_field input {
        width:98%;
    }
    
    @media (max-width: 767px) {
        .main{
            width:95%;
        }
        .large_field, .small_field {
            width:100%;
        }
        .contest_form_div{
            width:100%;
            margin-left:0;
        }
    }
</style>

<div id="loading_screen">
    <img alt="loading" src="http://kodekloud.s3.amazonaws.com/sites/5447ebb66e6f641987020000/3604f5c26997bea3de504de139debcec/loading.gif"/>
</div>

<div id="404_msg" style="display:none">
    <section class="call_to_action">
       <h3>OH NO! <strong>error 404</strong></h3>
       <h4>we are really sorry but the page you requested cannot be found.</h4>
    </section>
    
    <section class="call_to_action four-o-four"> <i class="fa fa-ambulance"></i>
       <h3>error 404 is nothing to really worry about...</h3>
       <h4>you may have mis-typed the URL, please check your spelling and try again.</h4>
  </section>
</div>

<div  id="main_content" style="display:none">
    <script id="contest_template" type="x-tmpl-mustache/text">
    <ul class="breadcrumb">
        <li><a href="/home">Home</a> </li>
        <li class="active">{{name}}</li>
    </ul>
    <div class="page_banner">
        {{name}}
    </div>
    <div class="inside_page_content">
        <img class="contest_image" src="{{alt_photo_url}}" />
     <br>
     <div class="contest_form_div">
           <p class="contest_description">{{description}}</p>

         <form id="contest_form">
            <input id="property_id" name="contest[property_id]" style="display:none" value="{{property_id}}"/>
            <input id="contest_id" name="contest[contest_id]" style="display:none" value="{{id}}" />
            <p class="large_field">
                <label for="first_name">First Name*</label><br/>
                <input id="first_name" type="text" name="contest[first_name]"required placeholder="First Name" />
            </p>
            <p class="large_field">
                <label for="last_name">Last Name*</label><br/>
                <input id="last_name" type="text" name="contest[last_name]" required placeholder="Last Name"/>
            </p>
          
            <p class="large_field">
                <label for="email">Email*</label><br />
                <input id="email" name="contest[email]" type="text" required placeholder="Email"/>
            </p>
            <p class="large_field">
                <label for="phone">Phone Number*</label><br />
                <input id="phone" name="contest[phone]" type="text" required placeholder="Phone Number"/>
            </p>
           
            <br>
            <br>
            
       
            <br>
                <label for="enterVerify">Enter the following number below to proceed: <div id="verifyNum"></div></label><br>
                <input type="hidden" value="701469" id="verifyNumHidden" name="verifyNumHidden" />
                <input type="text" id="enterVerify" name="enterVerify" />
            <br>

            <span>
                <br>
                <label for="newsletter_signup" style="display:none">Agree Terms</label>
              <input type="checkbox" class="info_check" id="newsletter_signup" name="contest[newsletter]"/>&nbsp;Yes, I would like to receive ongoing news related to events, promotions and special announcements from Sheridan Centre.
            </span>
            <br>
            <br>
            <span>
                <label for="agree_terms" style="display:none">Agree Terms</label>
              <input type="checkbox" class="info_check" id="agree_terms" required/>&nbsp;I agree to the <a href = "/pages/sheridancentre-rules-regulations"><u>rules and regulations</u></a>. 
            </span>
            <br>
            <br>
            <p>
                <button class="btn btn-primary" id="form_submit">Submit</button>
            </p>
        </form>
     </div>
    </div>
    </script>
</div>
<script>
    
  
    
    
    

    $(document).ready(function() {
        function renderAll (){
            var pathArray = window.location.pathname.split( '/' );
            var slug = "sheridancentre-sc-b2s-easy";
            var contestDetails = getContestBySlug(slug);
            if (contestDetails) {
                renderPageData('#main_content','#contest_template',contestDetails, 'contestDetails')
                $("#loading_screen").hide();
                $("#main_content").fadeIn( "fast");
                randomgen();
                jQuery(function($){
                   $("#birthday").mask("9999/99/99");
                });
                $('#contest_form').submit(function (e) {
                    e.preventDefault();
                    if($('#enterVerify').val() == $('#verifyNumHidden').val() ) {
                        if ($("#agree_terms").prop("checked") == true){
                            var e = verify_required_fields();
                            if (e == true){
                                if ($("#newsletter_signup").prop("checked") == true){
                                    var s = check_email();
                                    if (s == true){
                                        var email = $("#email").val();
                                        $('input[name="cm-iljuhu-iljuhu"]').attr('value',email);
                                         $.getJSON(
                                            "http://mobilefringe.createsend.com/t/d/s/iljuhu/?callback=?",
                                          $("#subForm").serialize(),
                                            function (data) {
                                                if (data.Status === 400) {
                                                    e.preventDefault();
                                                    alert("Please try again later.");
                                                } else { // 200
                                                    data = $("#contest_form").serialize();
                                                    submit_contest(data);
                                                      
                                                }
                                        });  
                                    } else {
            
                                        alert("Invaid E-mail address.")
                                    }
                                    
                                } else {
                                    data = $(this).serialize();
                                    submit_contest(data);
                                }    
                            } else {
                                alert("One or more required fields are blank! please fill in all fields marked with * and try again.")
                            }
                            
                            
                        } else {
                            alert("Please agree to the terms and conditions before submitting!")
                        }
                    }
                        else
                        {
                            alert("Please Enter Correct Verification Number");
                            randomgen();
                        }
                    
                    });
                } else {
                    $("#loading_screen").hide();
                    $("#404_msg").fadeIn( "fast");
                }
            
            
        }
        
        function submit_contest(data) {
            var propertyDetails = getPropertyDetails();
            var host = propertyDetails.mm_host
            $.ajax({
                url: host+"/newsletter_no_captcha",
                type: "POST",
                data: data,
                success: function(data) {
                    window.location.href="/contest_thank_you";
                },
                error: function(data){
                    // alert("There was an issue with submitting the contest entry. Please try again at a later time.")
                    window.location.href="/contest_thank_you";
                }
            });
        }
        
        function randomgen() {
            var rannumber='';
            for(ranNum=1; ranNum<=6; ranNum++){
                rannumber+=Math.floor(Math.random()*10).toString();
            }
            $('#verifyNum').html(rannumber);
            $('#verifyNumHidden').val(rannumber);
        }
    
        
        function renderPageData(container, template, collection, type){
            var item_list = [];
            var item_rendered = [];
            var template_html = $(template).html();
            Mustache.parse(template_html);   // optional, speeds up future uses
            if (type == "contestDetails"){
                collection.alt_photo_url = getImageURL(collection.photo_url)
                collection.property_id = getPropertyID();
                item_list.push(collection);
                collection = []
                collection = item_list;
            }
            $.each( collection , function( key, val ) {
                var rendered = Mustache.render(template_html,val);
                item_rendered.push(rendered);
    
            });
            
            $(container).show();
            $(container).html(item_rendered.join(''));
        };

        loadMallData(renderAll);  
        
        
        
        
        function verify_required_fields (){
            if ($("#first_name").val() != "" && $("#last_name").val() != "" && $("#email").val() != "" && $("#phone").val() != "" && $("#postal_code").val() != "" && $("#birthday").val() != "") {
                return true;
            } else {
                return false;
            }
        }
        
        function check_email (){
            var email = $("#fieldEmail").val();
            if( !validateEmail(email)) {
                return false
            } else {
                return true
            }
        }
        
        function validateEmail($email) {
          var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
          if( !emailReg.test( $email ) ) {
            return false;
          } else {
            return true;
          }
        }

    
  
    });
    
</script>