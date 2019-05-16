package su.svn.href.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import su.svn.href.models.UpdateValue;
import su.svn.href.models.dto.*;
import su.svn.href.repository.LocationRepository;

import java.util.Map;

import static su.svn.href.controllers.Constants.*;

@RestController()
@RequestMapping(value = REST_API + REST_V1_LOCATIONS)
public class LocationsRestController
{
    private LocationRepository locationRepository;

    @Autowired
    public LocationsRestController(LocationRepository locationRepository)
    {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public Mono<LocationDataTables> readFullLocations(@RequestParam("draw")   final Integer draw,
                                                      @RequestParam("start")  final Integer start,
                                                      @RequestParam("length") final Integer length,
                                                      @RequestParam("search[value]") final String searchValue,
                                                      @RequestParam("columns[0][search][value]") final String id,
                                                      @RequestParam("columns[1][search][value]") final String streetAddress,
                                                      @RequestParam("columns[2][search][value]") final String postalCode,
                                                      @RequestParam("columns[3][search][value]") final String city,
                                                      @RequestParam("columns[4][search][value]") final String stateProvince,
                                                      @RequestParam("order[0][column]") final Integer order,
                                                      @RequestParam("order[0][dir]") final String orderDir)
    {
        return locationRepository
            .findAll(start / length + 1, length)
            .collectList().flatMap(locationDtos ->
                locationRepository.count().flatMap(count ->
                    Mono.just(new LocationDataTables(draw, count, count, locationDtos))
                )
            );
    }

    @PostMapping(path = REST_UPDATE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<? extends Answer> updateLocation(UpdateValue<Long> body)
    {
        System.err.println("body = " + body);

//        return locationRepository.findById(updateValue.getPk())
//            .flatMap(locationDto -> {
//                String field = updateValue.getName().toUpperCase();
//
//                switch (field) {
//                    case "STREET_ADDRESS":
//                        locationDto.setStreetAddress(updateValue.getValue());
//                        return locationRepository
//                            .update(field, locationDto)
//                            .map(clientResponse -> {
//                                if (clientResponse.rawStatusCode() == HttpStatus.OK.value()) {
//                                    return new AnswerOk();
//                                }
//                                return new AnswerBadRequest("error 1");
//                            });
//                    default:
//                        return Mono.just(new AnswerOk());
//                }
//            });

        return Mono.just(new AnswerOk());
    }


    // {"timestamp":"2019-05-15T18:40:12.622+0000","path":"/rest/api/v1/locations/update","status":415,"error":"Unsupported Media Type","message":
    // "Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported for bodyType=java.util.Map<java.lang.String, java.lang.String>","trace":"org.springframework.web.server.UnsupportedMediaTypeStatusException: 415 UNSUPPORTED_MEDIA_TYPE \"Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported for bodyType=java.util.Map<java.lang.String, java.lang.String>\"\n\tat org.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver.readBody(AbstractMessageReaderArgumentResolver.java:213)\n\tat org.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver.readBody(AbstractMessageReaderArgumentResolver.java:125)\n\tat org.springframework.web.reactive.result.method.annotation.RequestBodyArgumentResolver.resolveArgument(RequestBodyArgumentResolver.java:66)\n\tat org.springframework.web.reactive.result.method.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:123)\n\tat org.springframework.web.reactive.result.method.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:191)\n\tat org.springframework.web.reactive.result.method.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:135)\n\tat org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.lambda$handle$1(RequestMappingHandlerAdapter.java:199)\n\tat reactor.core.publisher.MonoDefer.subscribe(MonoDefer.java:44)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:153)\n\tat reactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoPeekFuseable.subscribe(MonoPeekFuseable.java:74)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoPeekFuseable.subscribe(MonoPeekFuseable.java:74)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:150)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.MonoNext$NextSubscriber.onNext(MonoNext.java:76)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxConcatMap$ConcatMapImmediate.innerNext(FluxConcatMap.java:275)\n\tat reactor.core.publisher.FluxConcatMap$ConcatMapInner.onNext(FluxConcatMap.java:849)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:114)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.Operators$ScalarSubscription.request(Operators.java:2040)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:458)\n\tat reactor.core.publisher.FluxMap$MapSubscriber.request(FluxMap.java:155)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.request(FluxOnAssembly.java:458)\n\tat reactor.core.publisher.Operators$MultiSubscriptionSubscriber.set(Operators.java:1848)\n\tat reactor.core.publisher.Operators$MultiSubscriptionSubscriber.onSubscribe(Operators.java:1722)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:442)\n\tat reactor.core.publisher.FluxMap$MapSubscriber.onSubscribe(FluxMap.java:86)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:442)\n\tat reactor.core.publisher.MonoJust.subscribe(MonoJust.java:54)\n\tat reactor.core.publisher.MonoCallableOnAssembly.subscribe(MonoCallableOnAssembly.java:82)\n\tat reactor.core.publisher.MonoMap.subscribe(MonoMap.java:55)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.Mono.subscribe(Mono.java:3589)\n\tat reactor.core.publisher.FluxConcatMap$ConcatMapImmediate.drain(FluxConcatMap.java:442)\n\tat reactor.core.publisher.FluxConcatMap$ConcatMapImmediate.onSubscribe(FluxConcatMap.java:212)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onSubscribe(FluxOnAssembly.java:442)\n\tat reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:139)\n\tat reactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:63)\n\tat reactor.core.publisher.FluxOnAssembly.subscribe(FluxOnAssembly.java:164)\n\tat reactor.core.publisher.FluxConcatMap.subscribe(FluxConcatMap.java:121)\n\tat reactor.core.publisher.FluxOnAssembly.subscribe(FluxOnAssembly.java:164)\n\tat reactor.core.publisher.MonoNext.subscribe(MonoNext.java:40)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoSwitchIfEmpty.subscribe(MonoSwitchIfEmpty.java:44)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoDefer.subscribe(MonoDefer.java:52)\n\tat reactor.core.publisher.MonoOnAssembly.subscribe(MonoOnAssembly.java:76)\n\tat reactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:150)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:121)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1476)\n\tat reactor.core.publisher.MonoProcessor.onNext(MonoProcessor.java:389)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:121)\n\tat reactor.core.publisher.FluxContextStart$ContextStartSubscriber.onNext(FluxContextStart.java:103)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onNext(FluxMapFuseable.java:287)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.FluxFilterFuseable$FilterFuseableConditionalSubscriber.onNext(FluxFilterFuseable.java:331)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onNext(FluxOnAssembly.java:373)\n\tat reactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1476)\n\tat reactor.core.publisher.MonoCollectList$MonoBufferAllSubscriber.onComplete(MonoCollectList.java:118)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onComplete(FluxOnAssembly.java:383)\n\tat reactor.core.publisher.FluxMap$MapSubscriber.onComplete(FluxMap.java:136)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onComplete(FluxOnAssembly.java:383)\n\tat reactor.core.publisher.FluxPeek$PeekSubscriber.onComplete(FluxPeek.java:252)\n\tat reactor.core.publisher.FluxOnAssembly$OnAssemblySubscriber.onComplete(FluxOnAssembly.java:383)\n\tat reactor.core.publisher.FluxMap$MapSubscriber.onComplete(FluxMap.java:136)\n\tat reactor.netty.channel.FluxReceive.terminateReceiver(FluxReceive.java:378)\n\tat reactor.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:202)\n\tat reactor.netty.channel.FluxReceive.onInboundComplete(FluxReceive.java:343)\n\tat reactor.netty.channel.ChannelOperations.onInboundComplete(ChannelOperations.java:325)\n\tat reactor.netty.http.server.HttpServerOperations.onInboundNext(HttpServerOperations.java:441)\n\tat reactor.netty.channel.ChannelOperationsHandler.channelRead(ChannelOperationsHandler.java:141)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tat io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)\n\tat reactor.netty.http.server.HttpTrafficHandler.channelRead(HttpTrafficHandler.java:188)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tat io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)\n\tat io.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:438)\n\tat io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:323)\n\tat io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:297)\n\tat io.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:253)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tat io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)\n\tat io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1408)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tat io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tat io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:930)\n\tat io.netty.channel.epoll.AbstractEpollStreamChannel$EpollStreamUnsafe.epollInReady(AbstractEpollStreamChannel.java:799)\n\tat io.netty.channel.epoll.EpollEventLoop.processReady(EpollEventLoop.java:427)\n\tat io.netty.channel.epoll.EpollEventLoop.run(EpollEventLoop.java:328)\n\tat io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:905)\n\tat java.lang.Thread.run(Thread.java:748)\n\tSuppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: \nAssembly trace from producer [reactor.core.publisher.MonoError] :\n\treactor.core.publisher.Mono.error(Mono.java:261)\n\torg.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver.readBody(AbstractMessageReaderArgumentResolver.java:213)\n\torg.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver.readBody(AbstractMessageReaderArgumentResolver.java:125)\n\torg.springframework.web.reactive.result.method.annotation.RequestBodyArgumentResolver.resolveArgument(RequestBodyArgumentResolver.java:66)\n\torg.springframework.web.reactive.result.method.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:123)\n\torg.springframework.web.reactive.result.method.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:191)\n\torg.springframework.web.reactive.result.method.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:135)\n\torg.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.lambda$handle$1(RequestMappingHandlerAdapter.java:199)\n\treactor.core.publisher.MonoDefer.subscribe(MonoDefer.java:44)\n\treactor.core.publisher.MonoIgnoreThen$ThenIgnoreMain.drain(MonoIgnoreThen.java:153)\n\treactor.core.publisher.MonoIgnoreThen.subscribe(MonoIgnoreThen.java:56)\n\treactor.core.publisher.MonoPeekFuseable.subscribe(MonoPeekFuseable.java:74)\n\treactor.core.publisher.MonoPeekFuseable.subscribe(MonoPeekFuseable.java:74)\n\treactor.core.publisher.MonoOnErrorResume.subscribe(MonoOnErrorResume.java:44)\n\treactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:150)\n\treactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67)\n\treactor.core.publisher.MonoNext$NextSubscriber.onNext(MonoNext.java:76)\n\treactor.core.publisher.FluxConcatMap$ConcatMapImmediate.innerNext(FluxConcatMap.java:275)\n\treactor.core.publisher.FluxConcatMap$ConcatMapInner.onNext(FluxConcatMap.java:849)\n\treactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:114)\n\treactor.core.publisher.Operators$ScalarSubscription.request(Operators.java:2040)\n\treactor.core.publisher.FluxMap$MapSubscriber.request(FluxMap.java:155)\n\treactor.core.publisher.Operators$MultiSubscriptionSubscriber.set(Operators.java:1848)\n\treactor.core.publisher.Operators$MultiSubscriptionSubscriber.onSubscribe(Operators.java:1722)\n\treactor.core.publisher.FluxMap$MapSubscriber.onSubscribe(FluxMap.java:86)\n\treactor.core.publisher.MonoJust.subscribe(MonoJust.java:54)\n\treactor.core.publisher.MonoMap.subscribe(MonoMap.java:55)\n\treactor.core.publisher.Mono.subscribe(Mono.java:3589)\n\treactor.core.publisher.FluxConcatMap$ConcatMapImmediate.drain(FluxConcatMap.java:442)\n\treactor.core.publisher.FluxConcatMap$ConcatMapImmediate.onSubscribe(FluxConcatMap.java:212)\n\treactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:139)\n\treactor.core.publisher.FluxIterable.subscribe(FluxIterable.java:63)\n\treactor.core.publisher.FluxConcatMap.subscribe(FluxConcatMap.java:121)\n\treactor.core.publisher.MonoNext.subscribe(MonoNext.java:40)\n\treactor.core.publisher.MonoSwitchIfEmpty.subscribe(MonoSwitchIfEmpty.java:44)\n\treactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60)\n\treactor.core.publisher.MonoFlatMap.subscribe(MonoFlatMap.java:60)\n\treactor.core.publisher.MonoDefer.subscribe(MonoDefer.java:52)\n\treactor.core.publisher.MonoFlatMap$FlatMapMain.onNext(MonoFlatMap.java:150)\n\treactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:121)\n\treactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1476)\n\treactor.core.publisher.MonoProcessor.onNext(MonoProcessor.java:389)\n\treactor.core.publisher.FluxSwitchIfEmpty$SwitchIfEmptySubscriber.onNext(FluxSwitchIfEmpty.java:67)\n\treactor.core.publisher.FluxMapFuseable$MapFuseableSubscriber.onNext(FluxMapFuseable.java:121)\n\treactor.core.publisher.FluxContextStart$ContextStartSubscriber.onNext(FluxContextStart.java:103)\n\treactor.core.publisher.FluxMapFuseable$MapFuseableConditionalSubscriber.onNext(FluxMapFuseable.java:287)\n\treactor.core.publisher.FluxFilterFuseable$FilterFuseableConditionalSubscriber.onNext(FluxFilterFuseable.java:331)\n\treactor.core.publisher.Operators$MonoSubscriber.complete(Operators.java:1476)\n\treactor.core.publisher.MonoCollectList$MonoBufferAllSubscriber.onComplete(MonoCollectList.java:118)\n\treactor.core.publisher.FluxMap$MapSubscriber.onComplete(FluxMap.java:136)\n\treactor.core.publisher.FluxPeek$PeekSubscriber.onComplete(FluxPeek.java:252)\n\treactor.core.publisher.FluxMap$MapSubscriber.onComplete(FluxMap.java:136)\n\treactor.netty.channel.FluxReceive.terminateReceiver(FluxReceive.java:378)\n\treactor.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:202)\n\treactor.netty.channel.FluxReceive.onInboundComplete(FluxReceive.java:343)\n\treactor.netty.channel.ChannelOperations.onInboundComplete(ChannelOperations.java:325)\n\treactor.netty.http.server.HttpServerOperations.onInboundNext(HttpServerOperations.java:441)\n\treactor.netty.channel.ChannelOperationsHandler.channelRead(ChannelOperationsHandler.java:141)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tio.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)\n\treactor.netty.http.server.HttpTrafficHandler.channelRead(HttpTrafficHandler.java:188)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tio.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)\n\tio.netty.channel.CombinedChannelDuplexHandler$DelegatingChannelHandlerContext.fireChannelRead(CombinedChannelDuplexHandler.java:438)\n\tio.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:323)\n\tio.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:297)\n\tio.netty.channel.CombinedChannelDuplexHandler.channelRead(CombinedChannelDuplexHandler.java:253)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tio.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)\n\tio.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1408)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)\n\tio.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)\n\tio.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:930)\n\tio.netty.channel.epoll.AbstractEpollStreamChannel$EpollStreamUnsafe.epollInReady(AbstractEpollStreamChannel.java:799)\n\tio.netty.channel.epoll.EpollEventLoop.processReady(EpollEventLoop.java:427)\n\tio.netty.channel.epoll.EpollEventLoop.run(EpollEventLoop.java:328)\n\tio.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:905)\nError has been observed by the following operator(s):\n\t|_\tMono.error ⇢ org.springframework.web.reactive.result.method.annotation.AbstractMessageReaderArgumentResolver.readBody(AbstractMessageReaderArgumentResolver.java:213)\n\t|_\tMono.defaultIfEmpty ⇢ org.springframework.web.reactive.result.method.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:192)\n\t|_\tMono.doOnError ⇢ org.springframework.web.reactive.result.method.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:193)\n\t|_\tMono.zip ⇢ org.springframework.web.reactive.result.method.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:200)\n\t|_\tMono.flatMap ⇢ org.springframework.web.reactive.result.method.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:135)\n\t|_\tMono.defer ⇢ org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.handle(RequestMappingHandlerAdapter.java:199)\n\t|_\tMono.then ⇢ org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.handle(RequestMappingHandlerAdapter.java:199)\n\t|_\tMono.doOnNext ⇢ org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.handle(RequestMappingHandlerAdapter.java:200)\n\t|_\tMono.doOnNext ⇢ org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.handle(RequestMappingHandlerAdapter.java:201)\n\t|_\tMono.error ⇢ org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.handleException(RequestMappingHandlerAdapter.java:234)\n\t|_\tMono.onErrorResume ⇢ org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter.handle(RequestMappingHandlerAdapter.java:202)\n\t|_\tMono.flatMap ⇢ org.springframework.web.reactive.DispatcherHandler.handle(DispatcherHandler.java:151)\n\t|_\tMono.flatMap ⇢ org.springframework.web.reactive.DispatcherHandler.handle(DispatcherHandler.java:152)\n\t|_\tMono.defer ⇢ org.springframework.web.server.handler.DefaultWebFilterChain.filter(DefaultWebFilterChain.java:119)\n\t|_\tMono.flatMap ⇢ org.springframework.web.filter.reactive.HiddenHttpMethodFilter.filter(HiddenHttpMethodFilter.java:90)\n\t|_\tMono.defer ⇢ org.springframework.web.server.handler.DefaultWebFilterChain.filter(DefaultWebFilterChain.java:119)\n\n"}
//        System.out.println("updateValue = " + updateValue);
}
