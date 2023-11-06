package com.example.ease.util

import android.util.Patterns
fun onlyLetters( word :String ) : Boolean {
    val regex = "^[A-Za-z ]+$".toRegex()
    return regex.matches( word )
}
fun passwordSecurity( password :String ) : Boolean{
    val regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])((?=.*\\W)|(?=.*_))^[^ ]+.{7,}\$".toRegex()
    return regex.matches( password )
}
fun nicknameFormat( nickname : String ) : Boolean{
    val regex = "^[A-Za-z0-9_!%&$]+$".toRegex()

    return regex.matches( nickname )
}
fun validateName( name : String ) : RegisterValidation{
    if(name.isEmpty())
        return RegisterValidation.Failed("Name can't be empty")

    if(!onlyLetters( name ))
        return RegisterValidation.Failed("Name should only contain letters")

    return RegisterValidation.Success
}
fun validateSimple( field : String ) : RegisterValidation{
    if( field.isEmpty() )
        return RegisterValidation.Failed("This field can't be empty")

    return RegisterValidation.Success
}
fun validateNickname( nickname : String ) : RegisterValidation{
    if( nickname.isEmpty() )
        return RegisterValidation.Failed("Nickname can't be empty")

    if( !nicknameFormat( nickname ) )
        return RegisterValidation.Failed("Nickname must contain alphanumeric")

    return RegisterValidation.Success
}
fun validateDate( date : String ): RegisterValidation{
    if( date.isEmpty() )
        return RegisterValidation.Failed("Birthdate can't be empty")

    return RegisterValidation.Success
}
fun validateEmail(email : String) : RegisterValidation{
    if(email.isEmpty())
        return RegisterValidation.Failed("Email can't be empty")

    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        return  RegisterValidation.Failed("Write a valid email address")

    return RegisterValidation.Success
}
fun validatePassword(password : String) : RegisterValidation{
    if(password.isEmpty())
        return RegisterValidation.Failed("Password can't be empty")

    if(password.length < 8 )
        return RegisterValidation.Failed("Password should contain at least 8 characters")

    if(!passwordSecurity( password ) )
        return RegisterValidation.Failed("Password must contain at least an uppercase a lower case and a symbol")

    return RegisterValidation.Success
}
fun validateConfirmPassword(password : String, confirmPassword : String) : RegisterValidation{
    if(confirmPassword.isEmpty())
        return RegisterValidation.Failed("Password can't be empty")

    if(confirmPassword != password)
        return RegisterValidation.Failed("This field does not match with password")

    return RegisterValidation.Success
}