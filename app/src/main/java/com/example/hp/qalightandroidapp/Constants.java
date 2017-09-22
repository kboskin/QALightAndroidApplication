package com.example.hp.qalightandroidapp;

import android.support.v7.widget.RecyclerView;

import com.example.hp.qalightandroidapp.fragments.motivations.ModalHistoryPersonal;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hp on 007 07.09.2017.
 */

public class Constants {
    // class describes constants for WHOLE the application


    // variable to check if user has logged in
    public static String CHECK_IF_IS_AUTH_PASSED = "IsLoggedIn";

    // variable to configure google qury search
    public static String GOOGLE_STANDARD_WITH_NO_PARAMS_SEARCH_QUERY = "https://www.google.com/search?q=";

    // QAUrl
    public static String QALight_URL_To_Connect = "http://app.qalight.com.ua/?id=";

    //This List for testing recycleView in fragment motivations
    public static List<ModalHistoryPersonal> modalHistoryPersonal = Arrays.asList(
            new ModalHistoryPersonal("http://www.gamer.ru/system/attached_images/images/000/694/661/original/100461_ljofazyhoo_the_witcher_3_wild_hunt_getting.jpg","The Witcher", "QA Engineer","В течение игры под управлением игрока находится единственный, заранее заданный герой — ведьмак Геральт, который путешествует по игровому миру, общается с неигровыми персонажами и выполняет различные задания, в частности, связанные с поиском сокровищ и охотой на чудовищ. В нескольких особо важных с точки зрения сюжета эпизодах игры под управление игрока попадает героиня Цири; в это время игроку недоступны многие элементы геймплея, доступные при игре за Геральта, но у Цири есть уникальные способности, такие как телепортация."),
            new ModalHistoryPersonal("http://greenbelarus.info/files/724ddcf96ad9eb7193982ab005ef4b14.jpg", "Bill Gates", "Android Developer", "Информационные технологии – самая востребованная специальность на рынке труда: за IT-специалистами «охотятся», их перекупают и пытаются удержать не только финансовыми, но нематериальными благами. IT-шники — это не пришельцы из других планет, как многие склонны думать, и задача HR-ов найти способ действенной мотивации этих специалистов, живущих на закрытых сообществах и форумах и ценящих свободу выбора наравне с достойной оплатой их проффесиональных навыков."),
            new ModalHistoryPersonal("https://cdn.lifehacker.ru/wp-content/uploads/2016/04/photo-1431578500526-4d9613015464_1461679165-e1461679250549-630x315.jpg", "Чарльз Манген", "Business Analyst", "В 2007 году миллиардер Чарльз Мангер выступал перед выпускниками Университета Южной Калифорнии. «Нет сомнений, что многих из вас волнует вопрос, почему спикер такой старый, — сказал он. — Ну, ответ очевиден: «Спикер ещё не умер». К счастью, Мангер жив и сейчас. И его тогдашнее напутствие новоявленным юристам пригодится всем, кто хочет жить долго и счастливо. 1. Доверие, успех и славу нужно заработать\n" +
                    "\n" + "Очень простая идея. Можно сказать, золотое правило. Вы получите от мира то, что хотите, если представите себя на его месте. По большому счёту люди, которые живут согласно этому принципу, получают не только деньги и славу. Зарабатывая доверие людей, с которыми имеют дело, они зарабатывают уважение.\n" + "\n" + "Получать признание, оправдывая доверие других людей, очень приятно.\n" + "2. Учитесь восхищаться правильными людьми\n" + "\n" + "Вторая идея, до которой я дошёл очень рано: нет любви, кроме той, что основана на восхищении другим человеком. Этот человек не обязательно должен быть жив. Главное — то, на что он вас вдохновляет. Всю свою жизнь я прожил с этой идеей, и она оказалась очень, очень полезной для меня.\n" + "3. Приобретение знаний — это моральный долг, как и применение знаний на практике\n" + "\n" + "Из этого принципа вытекает одно важное следствие. Это значит, что вы должны решить превратить свою жизнь в постоянный поиск новых знаний. Без получения новых знаний вы не сможете стать человеком, который хорошо справляется со своими обязанностями. Вы не сможете далеко пойти, имея только те знания, что есть у вас сейчас.\n" + "\n" + "Если вы собираетесь двигаться дальше, значит, вы должны продолжать учиться даже после того, как покинете университет…")
            );
    // method to make fragments beautiful
    public static void setItemDecoration(RecyclerView recyclerView, int spanColumns)
    {
        int spanCount = spanColumns; // 3 columns
        int spacing = 25; // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
    }

}
