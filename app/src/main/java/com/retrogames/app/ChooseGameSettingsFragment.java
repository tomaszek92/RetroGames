package com.retrogames.app;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Tomasz on 30.12.13.
 */
public class ChooseGameSettingsFragment extends Fragment {

    private static String SOUND_STRING = "SOUND";
    private static String VIBRATION_STRING = "VIBRATION";

    public static boolean SOUND = true;
    public static boolean VIBRATION = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_game_settings, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        SOUND = sharedPreferences.getBoolean(SOUND_STRING, true);
        VIBRATION = sharedPreferences.getBoolean(VIBRATION_STRING, true);

        // ustawianie czcionki
        TextView textView = (TextView) view.findViewById(R.id.choose_game_settings_texView);
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), ChooseGameActivity.FONT);
        textView.setTypeface(font);

        TextView textViewSound = (TextView) view.findViewById(R.id.choose_game_settings_sound);
        textViewSound.setTypeface(font);

        // pobieranie i zmniejszanie obrazków
        Drawable soundDrawable = getResources().getDrawable(R.drawable.sound);
        Bitmap bitmap = ((BitmapDrawable)soundDrawable).getBitmap();
        Drawable drawableSoundScaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        Drawable soundNotDrawable = getResources().getDrawable(R.drawable.not_sound);
        bitmap = ((BitmapDrawable)soundNotDrawable).getBitmap();
        Drawable drawableNotSoundScaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));

        // ustawianie właściwego obrazka
        if (SOUND) {
            textViewSound.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableSoundScaled, null);
        }
        else {
            textViewSound.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableNotSoundScaled, null);
        }
        textViewSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textViewSound = (TextView) view.findViewById(R.id.choose_game_settings_sound);
                Drawable soundDrawable = getResources().getDrawable(R.drawable.sound);
                Bitmap bitmap = ((BitmapDrawable)soundDrawable).getBitmap();
                Drawable drawableSoundScaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
                Drawable soundNotDrawable = getResources().getDrawable(R.drawable.not_sound);
                bitmap = ((BitmapDrawable)soundNotDrawable).getBitmap();
                Drawable drawableNotSoundScaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));

                SOUND = !SOUND;
                if (SOUND) {
                    textViewSound.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableSoundScaled, null);
                }
                else {
                    textViewSound.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableNotSoundScaled, null);
                }

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(SOUND_STRING, SOUND);
                editor.commit();
            }
        });


        TextView textViewVibration = (TextView)view.findViewById(R.id.choose_game_settings_vibration);
        textViewVibration.setTypeface(font);
        Drawable vibrationDrawable = getResources().getDrawable(R.drawable.vibration);
        bitmap = ((BitmapDrawable)vibrationDrawable).getBitmap();
        Drawable drawableVibrationScaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
        Drawable vibrationNotDrawable = getResources().getDrawable(R.drawable.not_vibration);
        bitmap = ((BitmapDrawable)vibrationNotDrawable).getBitmap();
        Drawable drawableNotVibration = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));

        if (VIBRATION) {
            textViewVibration.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableVibrationScaled, null);
        }
        else {
            textViewVibration.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableNotVibration, null);
        }

        textViewVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textViewVibration = (TextView)view.findViewById(R.id.choose_game_settings_vibration);
                Drawable vibrationDrawable = getResources().getDrawable(R.drawable.vibration);
                Bitmap bitmap = ((BitmapDrawable)vibrationDrawable).getBitmap();
                Drawable drawableVibrationScaled = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));
                Drawable vibrationNotDrawable = getResources().getDrawable(R.drawable.not_vibration);
                bitmap = ((BitmapDrawable)vibrationNotDrawable).getBitmap();
                Drawable drawableNotVibration = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 70, 70, true));

                VIBRATION = !VIBRATION;

                if (VIBRATION) {
                    textViewVibration.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableVibrationScaled, null);
                }
                else {
                    textViewVibration.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableNotVibration, null);
                }

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(VIBRATION_STRING, VIBRATION);
                editor.commit();
            }
        });

        return view;
    }
}
