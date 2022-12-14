// Example starter JavaScript for disabling form submissions if there are invalid fields
(function () {
    'use strict'

    // Fetch all the forms we want to apply custom Bootstrap validation styles to
    const forms = document.querySelectorAll('.needs-validation')
    const hintLogin = document.querySelector(".hint-login")

    // Loop over them and prevent submission
    forms.forEach(form => {
            form.addEventListener('submit', e => {
                if (!form.checkValidity()) {
                    e.preventDefault()
                    e.stopPropagation()
                    console.log(hintLogin)
                    if (hintLogin) hintLogin.style.display = "block"

                }

                form.classList.add('was-validated')
            }, false)
        })
})()