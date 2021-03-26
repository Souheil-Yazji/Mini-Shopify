<template>
  <div class="main-container">
    <form>
      <div class="box-container">
        <h2 class="heading">Login</h2>
        <div class="form-fields">
          <input id="email" name="email" type="text" placeholder="Email Address">
        </div>
        <div class="form-fields">
          <input id="password" name="password" type="text" placeholder="Password">
        </div>
        <div class="form-fields">
          <button class="signIn" name="commit" type="submit">
            Sign In
          </button>
        </div>
      </div>
    </form>
    <div class="footer">
      <p>Don't have an account?
        <router-link to=signup> Create one now</router-link>
      </p>
    </div>
  </div>
</template>

<script>

</script>

<style scoped>
</style>