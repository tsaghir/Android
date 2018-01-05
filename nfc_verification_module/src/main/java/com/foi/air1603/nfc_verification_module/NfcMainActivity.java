package com.foi.air1603.nfc_verification_module;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Karlo on 20.1.2017..
 */

public class NfcMainActivity extends Activity implements NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback {

    //The array list to hold our messages
    private ArrayList<String> messagesReceivedArray = new ArrayList<>();
    private String messageToSend;

    private NfcAdapter mNfcAdapter;
    private NfcVerificationCaller nfcVerificationCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nfcVerificationCaller = NfcVerificationCaller.getInstance();

        // Received NDEF message enters this block (VLASNIK)
        if (getIntent().getAction() != null && getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            handleNfcIntent(getIntent());
        }

        setContentView(R.layout.activity_nfc_main);

        //Check if NFC is available on device
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter != null) {
            //This will refer back to createNdefMessage for what it will send
            mNfcAdapter.setNdefPushMessageCallback(this, this);

            //This will be called if the message is sent successfully
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        } else {
            Toast.makeText(this, "NFC nije dostupan na ovom uređaju. Molim Vas, koristite drugi način verifikacije!",
                    Toast.LENGTH_SHORT).show();
        }

        // TODO: ubaciti informacije o appointmentu koje trebaju ići
        messageToSend = getIntent().getExtras().getString("pass");
    }


    /////////// DIO ZA SLANJE - KORISNIK ////////
    public NdefRecord[] createRecords() {
        // Šaljemo jednu poruku(id) i jedan ekstra za createApplicationRecord
        NdefRecord[] records = new NdefRecord[1 + 1];

        byte[] payload = messageToSend.getBytes(Charset.forName("UTF-8"));
        NdefRecord record = NdefRecord.createMime("text/plain", payload);

        records[0] = record;
        records[1] = NdefRecord.createApplicationRecord(getPackageName());

        System.out.println("messageToSend " + messageToSend);
        return records;
    }

    /**
     * This will be called when another NFC capable device is detected.
     *
     * @param event
     * @return
     */
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        System.out.println("---------------createNdefMessage:KORISNIK");

        if (messageToSend == null) {
            return null;
        }

        NdefRecord[] recordsToAttach = createRecords();
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }

    /**
     * This is called when the system detects that our NdefMessage was successfully sent
     *
     * @param event
     */
    @Override
    public void onNdefPushComplete(NfcEvent event) {
        System.out.println("---------------onNdefPushComplete:KORISNIK");
        nfcVerificationCaller.mNfcVerificationHandler.onResultArrived(0);
        finish();
    }

    ///////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////// DIO ZA PRIHVAĆANJE - VLASNIK ////////
    private void handleNfcIntent(Intent NfcIntent) {
        String newMessage = "";
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction())) {
            Parcelable[] receivedArray =
                    NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (receivedArray != null) {
                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record : attachedRecords) {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Application Record)
                    if (string.equals(getPackageName())) {
                        continue;
                    }
                    newMessage = string;
                }

                //Toast.makeText(this, "Message From User: " + newMessage, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "NFC Error", Toast.LENGTH_LONG).show();
            }
            // vracanje na verification klasu za VLASNIKA
            if(nfcVerificationCaller.mNfcVerificationHandler == null){
                Toast.makeText(this,
                        "Neuspjela verifikacija NFC-om. Otvorite 'Moje rezervacije' i pokušajte ponovo", Toast.LENGTH_LONG).show();
            } else {
                nfcVerificationCaller.mNfcVerificationHandler.onResultArrived(Integer.parseInt(newMessage));
            }
            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handleNfcIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        handleNfcIntent(intent);
    }
    ///////////////////////////////////////////////////////
}