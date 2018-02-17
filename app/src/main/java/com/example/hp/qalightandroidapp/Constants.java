package com.example.hp.qalightandroidapp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.hp.qalightandroidapp.fragments.motivations.RecycleViewMotivations.ModalHistoryPersonal;
import com.example.hp.qalightandroidapp.helpers.GridSpacingItemDecoration;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 007 07.09.2017.
 */

public class Constants {
    // class describes constants for WHOLE the application


    // variable to check if user has logged in
    public final static String CHECK_IF_IS_AUTH_PASSED = "IsLoggedIn";

    // variable to configure google query search
    public final static String GOOGLE_STANDARD_WITH_NO_PARAMS_SEARCH_QUERY = "https://www.google.com/search?q=";

    // QAUrl
    public final static String QALight_URL_To_Connect = "http://app.qalight.com.ua/?";

    // Failure value for incorrect request id
    public final static String CHAR_SEQUENCE_FAILURE_VALUE_FOR_RESPONSE = "false";

    // Failure value for incorrect request id
    public final static String HELLO_MESSAGE_FOR_USER = "hello_message_for_user";

    // ResultCodeForCloudMessaging
    public static final int RESULT_CODE_FCM = 0;

    // Extras for notification, to open specific fragment in activity
    public static final String EXTRA_NOTIFICATION_FRAGMENT = "notifications_fragment";

    // Extras to pass into push activity
    // title
    public static final String EXTRA_NOTIFICATION_TITLE = "title";
    // description
    public static final String EXTRA_NOTIFICATION_DESCRIPTION = "description";
    // status
    public static final String EXTRA_NOTIFICATION_STATUS = "status";
    // status
    public static final String EXTRA_LOGIN_CODE = "code";
    // Download directory absolute path
    public static final String DOWNLOAD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();


    //This List for testing recycleView in fragment motivations
    public static List<ModalHistoryPersonal> modalHistoryPersonal = Arrays.asList(
            new ModalHistoryPersonal("http://www.gamer.ru/system/attached_images/images/000/694/661/original/100461_ljofazyhoo_the_witcher_3_wild_hunt_getting.jpg", "The Witcher", "QA Engineer", "В течение игры под управлением игрока находится единственный, заранее заданный герой — ведьмак Геральт, который путешествует по игровому миру, общается с неигровыми персонажами и выполняет различные задания, в частности, связанные с поиском сокровищ и охотой на чудовищ. В нескольких особо важных с точки зрения сюжета эпизодах игры под управление игрока попадает героиня Цири; в это время игроку недоступны многие элементы геймплея, доступные при игре за Геральта, но у Цири есть уникальные способности, такие как телепортация."),
            new ModalHistoryPersonal("http://greenbelarus.info/files/724ddcf96ad9eb7193982ab005ef4b14.jpg", "Bill Gates", "Android Developer", "Информационные технологии – самая востребованная специальность на рынке труда: за IT-специалистами «охотятся», их перекупают и пытаются удержать не только финансовыми, но нематериальными благами. IT-шники — это не пришельцы из других планет, как многие склонны думать, и задача HR-ов найти способ действенной мотивации этих специалистов, живущих на закрытых сообществах и форумах и ценящих свободу выбора наравне с достойной оплатой их проффесиональных навыков."),
            new ModalHistoryPersonal("https://cdn.lifehacker.ru/wp-content/uploads/2016/04/photo-1431578500526-4d9613015464_1461679165-e1461679250549-630x315.jpg", "Чарльз Манген", "Business Analyst", "В 2007 году миллиардер Чарльз Мангер выступал перед выпускниками Университета Южной Калифорнии. «Нет сомнений, что многих из вас волнует вопрос, почему спикер такой старый, — сказал он. — Ну, ответ очевиден: «Спикер ещё не умер». К счастью, Мангер жив и сейчас. И его тогдашнее напутствие новоявленным юристам пригодится всем, кто хочет жить долго и счастливо. 1. Доверие, успех и славу нужно заработать\n" +
                    "\n" + "Очень простая идея. Можно сказать, золотое правило. Вы получите от мира то, что хотите, если представите себя на его месте. По большому счёту люди, которые живут согласно этому принципу, получают не только деньги и славу. Зарабатывая доверие людей, с которыми имеют дело, они зарабатывают уважение.\n" + "\n" + "Получать признание, оправдывая доверие других людей, очень приятно.\n" + "2. Учитесь восхищаться правильными людьми\n" + "\n" + "Вторая идея, до которой я дошёл очень рано: нет любви, кроме той, что основана на восхищении другим человеком. Этот человек не обязательно должен быть жив. Главное — то, на что он вас вдохновляет. Всю свою жизнь я прожил с этой идеей, и она оказалась очень, очень полезной для меня.\n" + "3. Приобретение знаний — это моральный долг, как и применение знаний на практике\n" + "\n" + "Из этого принципа вытекает одно важное следствие. Это значит, что вы должны решить превратить свою жизнь в постоянный поиск новых знаний. Без получения новых знаний вы не сможете стать человеком, который хорошо справляется со своими обязанностями. Вы не сможете далеко пойти, имея только те знания, что есть у вас сейчас.\n" + "\n" + "Если вы собираетесь двигаться дальше, значит, вы должны продолжать учиться даже после того, как покинете университет…")
    );

    // method to make fragments beautiful
    public static void setItemDecoration(RecyclerView recyclerView, int spanColumns) {
        int spanCount = spanColumns; // 3 columns
        int spacing = 25; // 50px
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, true));
    }

    // method to parse data to proper format, returns an array of ints
    public static int[] parseDateToProperFormat(String date) {
        int year = Integer.parseInt(date.substring(0, 4)); // getting a year here
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3)); // getting a month here
        int day = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1)); // getting a day here

        return new int[]{year, month, day};
    }

    public static int[] parseDateAndHoursToProperFormat(String date) {
        int year = Integer.parseInt(date.substring(0, 4)); // getting a year here
        int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.indexOf("-") + 3)); // getting a month here
        int day = Integer.parseInt(date.substring(date.lastIndexOf("-") + 1, date.indexOf(" "))); // getting a day here
        int hours = Integer.parseInt(date.substring(date.indexOf(" ") + 1, date.indexOf(":")));
        int minutes = Integer.parseInt(date.substring(date.indexOf(":") + 1, date.lastIndexOf(":")));

        return new int[]{year, month, day, hours, minutes};
    }

    public static void setTypefaceToTextView(TextView view, Context context) {
        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/sf.ttf");
        view.setTypeface(face);
    }

    public static void addSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        // colors for swipe
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorOrange,
                R.color.colorGreen);

    }

    public static String performSearch(String fileName) {
        File directory = new File(DOWNLOAD_PATH);
        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (fileName.equals(files[i].getName()))
                return files[i].getAbsolutePath();
        }
        return null;
    }

}

