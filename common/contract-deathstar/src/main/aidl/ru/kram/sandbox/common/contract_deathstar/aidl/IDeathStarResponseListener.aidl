package ru.kram.sandbox.common.contract_deathstar.aidl;

import ru.kram.sandbox.common.contract_deathstar.aidl.DeathStar;

interface IDeathStarResponseListener {
    void onResponse(in DeathStar deathStar);
}