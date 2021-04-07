<template>
	<div class="text">
		四级路由
	</div>
</template>

<script>
	export default {
		name:"ShowFundArticle",
		data() {
			return {
				
			};
		},
		beforeRouteEnter (to, from, next) {
			alert("哈哈")
			next()
		}
	}
</script>

<style>

</style>
