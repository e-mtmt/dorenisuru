$(function() {
	if ($("#invalid-vote")[0]) {
		$("#add-vote-modal").removeClass("fade").modal("show").addClass("fade");
	}
	$(":radio[id^=choiceFeelings]:checked").parent("label").addClass("active");
});
