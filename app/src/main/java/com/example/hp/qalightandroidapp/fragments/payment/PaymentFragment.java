package com.example.hp.qalightandroidapp.fragments.payment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import com.example.hp.qalightandroidapp.R;

public class PaymentFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        return view;
    }




    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pay mPay = new Pay(getContext(), new Api.ApiEventListener<ua.privatbank.payoneclicklib.Api>() {
            @Override
            public void onApiStartRequest() {
                *//* ваш код обработки начала отправки очереди запросов*//*
            }

            @Override
            public void onApiFinishRequest() {
                *//* ваш код обработки завершения отправки очереди запросов *//*
            }

            @Override
            public void onApiError(ua.privatbank.payoneclicklib.Api api, Message.ErrorCode code) {
                *//* ваш код обработки ошибок которые приходят от сервера (список есть ниже)*//*
                *//* код ошибки нужно получать этим методом: api.getLastServerFailCode() *//*
            }
        }, [ UUID ], mMerchId);

                *//* ваш код *//*

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        return view;
    }


    private void setPayData()
    {
        // initializing payData
        PayData payData = new PayData();
        payData.setCcy("UAH");
        payData.setAmount("0.01");
        payData.setDescription("test");



    }
    // method to pay using privat
    private void pay(PayData payData){
        mPay.pay(payData, new Pay.PaymentCallBack() {
            @Override
            public void onPaymentSuccess() {
                *//* ваш код обработки успешных платежей без  проверки  отп  *//*
            }

            @Override
            public void onReceiveOtpSend(Pay.OtpCheckListener otpListener) {
                *//* будет вызван когда отп пароль будет отправлен на указанный телефон*//*
                otpListener.onOtpCheck(phone, otpCode, new Pay.OtpCallBack() {
                    @Override
                    public void onOtpSuccess() {
                *//*  будет вызван если отп верный*//*
                    }

                    @Override
                    public void onOtpFailed() {
                *//*  будет вызван если отп не верный *//*
                    }
                });
            }
            @Override
            public void onPaymentFailed() {
                *//* ваш код обработки ошибок при осуществлении платежа*//*
            }

            @Override
            public void onPaymentProcessing() {
                *//*  ваш код обработки ошибки когда платеж находится в обработке *//*
            }

            @Override
            public void onBackPressed() {
                *//* пользователь нажал кнопку “Назад” в браузере *//*
            }
        });
    }*/
}
