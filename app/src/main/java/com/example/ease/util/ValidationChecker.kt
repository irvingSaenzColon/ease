package com.example.ease.util

import android.util.Patterns
fun onlyLetters( word :String ) : Boolean {
    val regex = "^[A-Za-zÁÉÍÓÚáéíóú ]+$".toRegex()
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
fun decimalFormat( decimal : String ) : Boolean{
    val regex = "^([0-9]+)(\\.)*([0-9])*\$".toRegex()
    return regex.matches( decimal )
}
fun numberFormat( number : String ) : Boolean{
    val regex = "^([0-9]+)$".toRegex()
    return regex.matches( number )
}

fun expireDateFormat( expireDate: String ) : Boolean{
    val regex = "^[0-9]{2}\\/[0-9]{2}\$".toRegex()
    return regex.matches( expireDate )
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
fun validateNumber(number : String) : RegisterValidation{
    if(number.isEmpty())
        return RegisterValidation.Failed("Number can't be empty")

    if(!numberFormat( number ))
        return RegisterValidation.Failed("This field only accepts numbers")

    return RegisterValidation.Success
}

fun validateCardNumber(number : String) : RegisterValidation{
    if(number.isEmpty())
        return RegisterValidation.Failed("Card number can't be empty")

    if(number.length >=25)
        return RegisterValidation.Failed("Card number is way too long")

    if(!numberFormat( number ))
        return RegisterValidation.Failed("Card number must contain only numbers")

    return RegisterValidation.Success
}

fun validateZipCode( zip : String ) : RegisterValidation{
    if(zip.isEmpty())
        return RegisterValidation.Failed("Zip code can't be empty")

    if(zip.length > 3)
        return RegisterValidation.Failed("Zip code is way too long")

    if(!numberFormat( zip ))
        return RegisterValidation.Failed("Zip code must contain only numbers")

    return RegisterValidation.Success
}

fun validateExpireValidation(expireDate : String) : RegisterValidation{
    if(expireDate.isEmpty())
        return RegisterValidation.Failed("Expire date can't be empty")

    if(expireDate.length > 5)
        return RegisterValidation.Failed("Expire date is way to long")

    if(!expireDateFormat( expireDate ))
        return RegisterValidation.Failed("Expire date must contain only numbers and follow MM/YY format")

    return RegisterValidation.Success
}

fun validateDecimal(decimal: String) : RegisterValidation{
    if(decimal.isEmpty())
        return RegisterValidation.Failed("Password can't be empty")

    if(! decimalFormat( decimal ) )
        return RegisterValidation.Failed("This field does not match with password")

    return RegisterValidation.Success
}