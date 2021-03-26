<template>
  <div class="main-container">
    <form>
      <div class="box-container">
        <h2 class="heading">
          Create Your Account
        </h2>
        <div class="form-fields">
          <input id="user_name" name="username" type="text" placeholder="Full name">
        </div>
        <div class="form-fields">
          <input id="email" name="email" type="text" placeholder="Email Address">
        </div>
        <div class="form-fields">
          <input id="password" name="password" type="text" placeholder="Password">
        </div>
        <div class="form-fields">
          <button class="createaccount" name="commit" type="submit">
            Create your account
          </button>
        </div>
        <div class="login-choice"><span>or sign up with</span></div>
        <o-auth/>
        <div>
          <p class="center">
            By signing up you agree to the
            <a href="#">Terms of Service</a>.
          </p>
        </div>
      </div>
    </form>
    <div class="footer">
      <p>Already have an account?
        <router-link to=Login> Login</router-link>
      </p>
    </div>
  </div>
</template>

<script>
</script>

<style scoped>
</style>