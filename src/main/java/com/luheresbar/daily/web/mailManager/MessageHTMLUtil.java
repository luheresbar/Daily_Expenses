package com.luheresbar.daily.web.mailManager;

public class MessageHTMLUtil {

    public static final String TEMPLATE_PRUEBA = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
            "    <title>Recuperación de Contraseña</title>\n" +
            "    <style>\n" +
            "        body {\n" +
            "            font-family: Arial, sans-serif;\n" +
            "            background-color: #f4f4f4;\n" +
            "            margin: 0;\n" +
            "            padding: 0;\n" +
            "        }\n" +
            "\n" +
            "        .container {\n" +
            "            max-width: 600px;\n" +
            "            margin: 20px auto;\n" +
            "            background-color: #fff;\n" +
            "            padding: 20px;\n" +
            "            border-radius: 5px;\n" +
            "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
            "        }\n" +
            "\n" +
            "        h2 {\n" +
            "            color: #333;\n" +
            "        }\n" +
            "\n" +
            "        p {\n" +
            "            color: #555;\n" +
            "        }\n" +
            "\n" +
            "        .button {\n" +
            "            display: inline-block;\n" +
            "            padding: 10px 20px;\n" +
            "            background-color: #007bff;\n" +
            "            color: #ffffff !important;\n" +
            "            text-decoration: none;\n" +
            "            border-radius: 3px;\n" +
            "        }\n" +
            "\n" +
            "        .footer {\n" +
            "            margin-top: 20px;\n" +
            "            text-align: center;\n" +
            "            color: #777;\n" +
            "        }\n" +
            "    </style>\n" +
            "</head>\n" +
            "<body>\n" +
            "    <div class=\"container\">\n" +
            "        <h2>Recuperación de Contraseña</h2>\n" +
            "        <p>Haz clic en el siguiente enlace para recuperar tu contraseña:</p>\n" +
            "        <a class=\"button\" href=\"{0}\" target=\"_blank\">Recuperar Contraseña</a>\n" +
            "        <div class=\"footer\">\n" +
            "            <p>Este correo electrónico fue enviado automáticamente. Por favor, no respondas a este mensaje.</p>\n" +
            "        </div>\n" +
            "    </div>\n" +
            "</body>\n" +
            "</html>\n";

}
