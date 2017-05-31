$(function() {
	$(".noticeMessage").fadeIn("slow");

	$("form").submit(function() {
		$(":submit", this).prop("disabled", true);
	});

	$(":radio[id^=choiceFeelings]:checked").parent("label").addClass("active");

	$("[data-toggle='tooltip']").tooltip({
		container : "body",
		delay : {
			"show" : 700,
			"hide" : 100
		},
		trigger : "hover"
	});

	$("#topic-url").on("click", function() {
		$(this).select();
		document.execCommand("copy");
		$(this).tooltip({
			title : "コピーしました",
			container : "body",
			trigger : "focus"
		}).tooltip("show");
	});
});
