# multimedia Hw2
Description:
This assignment is to practice the RTP protocol. Given a video file, you have to write programs that are able to read the file, packetize it with RTP/UDP/IP and send those packets to a remote receiver host.  
The receiver host has to reconstruct the video file into the original form (may have to accommodate some packet loss), then use a given video player to play it back (not necessary immediately).   
(The video is not compressed, so unless you compress it before transmission, it is not practical to playback the video in real time)

* Task: https://drive.google.com/file/d/1WaQOrm3CO6tOkQ2e1CCQpR8QEuZIZflG/view?usp=sharing
* Report: https://drive.google.com/file/d/15t1USjfq6RveANaRFXmmARFPttpW150e/view?usp=sharing

There are 2 code in here,  
there is server packetize video and compress the file that later send it to the client  
the client will decrompress, then join the packetize file so the video can be played  
