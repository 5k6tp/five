function loginApi(data) {
    return $axios({
      'url': '/user/login',
      'method': 'post',
      data
    })
  }

  function sendMailApi(data) {
    return $axios({
      'url': '/user/sendMail',
      'method': 'post',
      data
    })
  }


function loginoutApi() {
  return $axios({
    'url': '/user/loginout',
    'method': 'post',
  })
}

  