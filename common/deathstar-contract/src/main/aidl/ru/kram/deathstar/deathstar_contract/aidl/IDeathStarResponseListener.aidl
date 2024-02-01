package ru.kram.deathstar.deathstar_contract.aidl;

import ru.kram.deathstar.deathstar_contract.aidl.DeathStar;

interface IDeathStarResponseListener {
    void onResponse(in DeathStar deathStar);
}