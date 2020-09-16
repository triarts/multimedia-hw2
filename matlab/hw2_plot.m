
clc; clear all ;

%% -------------- client
fid = fopen('report_packet_client.txt');

x_client = [];
tline = fgetl(fid);
while ischar(tline)
%     disp(tline)
    %temp = double(x);
    x_client = [x_client, str2num(tline)];
    tline = fgetl(fid);
end
% disp('packet_client l'+length(x_client));
% disp(x);
fclose(fid);

fid = fopen('report_timestamp_client.txt');
y_client = [];
tline = fgetl(fid);
while ischar(tline)
%     disp(tline)
%     temp = double(tline);
%     y = [y, str2double(tline)-1546071200000];
%     newStr = extractAfter(tline,3);
    newStr = tline;
    y_client = [y_client, str2num(newStr)];
    tline = fgetl(fid);
end
fclose(fid);
% disp('time_client l'+length(y_client));

newY_client = [];
for v = 1:1:length(y_client)
%    disp(v)
   if v==1
         newY_client = [newY_client,0];
   else
       newY_client = [newY_client, y_client(v)-y_client(v-1)];
   end
end
% disp(newY_client);

%% -------------- server
fid = fopen('report_packet_server.txt');
x_server = [];
tline = fgetl(fid);
while ischar(tline)
%     disp(tline)
    %temp = double(x);
    x_server = [x_server, str2num(tline)];
    tline = fgetl(fid);
end
fclose(fid);
% disp(length(x));
% disp(x);



fid = fopen('report_timestamp_server.txt');

y_server = [];
tline = fgetl(fid);
while ischar(tline)
%     disp(tline)
%     temp = double(tline);
%     y = [y, str2double(tline)-1546071200000];
%     newStr = extractAfter(tline,3);
    newStr = tline;
    y_server = [y_server, str2num(newStr)];
    tline = fgetl(fid);
end
fclose(fid);
% disp(length(y));
%  disp(y);
%  disp(y(3));

newY_server = [];
for v = 1:1:length(y_server)
%    disp(v)
   if v==1
         newY_server = [newY_server,0];
   else
       newY_server = [newY_server, y_server(v)-y_server(v-1)];
   end
end
% disp(newY);

disp('total packet send by server : ');
disp(x_server(length(x_server)))
disp('timestamp last packet send : in system.nanoTime()');
disp(y_server(length(y_server)))

disp('total packet received by client : ');
disp(x_client(length(x_client)))
disp('timestamp last received by client : in system.nanoTime()');
disp(y_client(length(y_client)))


disp('total time transmit : second');
disp((y_client(length(y_client))-y_server(1))/1000000000)
%
figure('name','inter-frame jitter');
hold on
% plot(x_server,newY_server,'--b');

plot(x_client,newY_client,'-r');

hold off

% xticks(x_server);
% title('server -- blue | client - red');
title('client - red');
xlabel('packet');
ylabel('jitter');

