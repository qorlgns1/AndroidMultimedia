package naver.rlgns1129.androidmultimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class LocationActivity extends AppCompatActivity implements AutoPermissionsListener {
    //디자인한 뷰를 저장하기 위한 변수
    private TextView lblLocation;
    private Button btnLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        AutoPermissions.Companion.loadAllPermissions(this, 101);

        //뷰 찾아오기
        lblLocation = findViewById(R.id.lblLocation);
        btnLocation = findViewById(R.id.btnLocation);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //AutoPermissions의 메소드를 호출하도록 설정
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }


    @Override
    public void onDenied(int i, String[] permissions) {
        Toast.makeText(this, "권한 사용을 거부함", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int i, String[] permissions) {
        Toast.makeText(this, "권한 사용을 허용함", Toast.LENGTH_LONG).show();
    }

    class GPSListener implements LocationListener {
        public void onLocationChanged(Location location) {

            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            String msg = "내 위치 -> Latitude : " + latitude + "\nLongitude:" + longitude;
            lblLocation.setText(msg);
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }

    public void startLocationService() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                String message = "최근 위치 -> Latitude : " + latitude + "\nLongitude:" + longitude;

                lblLocation.setText(message);
            }
            GPSListener gpsListener = new GPSListener();

            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime, minDistance, gpsListener);

            Toast.makeText(getApplicationContext(), "내 위치확인 요청함",
                    Toast.LENGTH_SHORT).show();

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}

