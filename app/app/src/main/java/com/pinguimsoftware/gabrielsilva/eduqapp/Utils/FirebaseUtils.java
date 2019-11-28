package com.pinguimsoftware.gabrielsilva.eduqapp.Utils;

import android.content.Context;
import android.widget.Toast;

public class FirebaseUtils {

	public static void getError(Context context, String error){


		if (error.contains("least 6 characters")){

			Toast.makeText(context,"Digite uma senha maior que 8 characters",Toast.LENGTH_LONG).show();

		}
		else if(error.contains("address is badly")){

			Toast.makeText(context,"E-mail inválido",Toast.LENGTH_LONG).show();

		}
		else if(error.contains("interrupted connection")){

			Toast.makeText(context,"Sem conexão",Toast.LENGTH_LONG).show();

		}else if(error.contains("password is invalid")){

			Toast.makeText(context,"Senha invalida",Toast.LENGTH_LONG).show();

		} else if(error.contains("There is no user")){

			Toast.makeText(context,"E-mail não cadastrado",Toast.LENGTH_LONG).show();

		}
		else if(error.contains("address is already")){

			Toast.makeText(context,"E-mail já cadastrado",Toast.LENGTH_LONG).show();

		}
		else if(error.contains("INVALID_EMAIL")){

			Toast.makeText(context,"E-mail inválido",Toast.LENGTH_LONG).show();

		}
		else if(error.contains("EMAIL_NOT_FOUND")){

			Toast.makeText(context,"E-mail não cadastrado",Toast.LENGTH_LONG).show();

		}
		else{

			Toast.makeText(context,error,Toast.LENGTH_LONG).show();


		}

	}
}
