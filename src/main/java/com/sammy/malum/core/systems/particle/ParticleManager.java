package com.sammy.malum.core.systems.particle;

import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;

import java.awt.*;
import java.util.Random;

public class ParticleManager
{
    public static class ParticleBuilder
    {
        static Random random = new Random();
    
        ParticleType<?> type;
        MalumParticleData data;
        double vx = 0, vy = 0, vz = 0;
        double dx = 0, dy = 0, dz = 0;
        double maxXSpeed = 0, maxYSpeed = 0, maxZSpeed = 0;
        double maxXDist = 0, maxYDist = 0, maxZDist = 0;
        
        protected ParticleBuilder(ParticleType<?> type)
        {
            this.type = type;
            this.data = new MalumParticleData(type);
        }
    
        public ParticleBuilder setColor(float r, float g, float b)
        {
            setColor(r, g, b, data.a1, r, g, b, data.a2);
            return this;
        }
    
        public ParticleBuilder setColor(float r, float g, float b, float a)
        {
            setColor(r, g, b, a, r, g, b, a);
            return this;
        }
    
        public ParticleBuilder setColor(float r, float g, float b, float a1, float a2)
        {
            setColor(r, g, b, a1, r, g, b, a2);
            return this;
        }
    
        public ParticleBuilder setColor(float r1, float g1, float b1, float r2, float g2, float b2)
        {
            setColor(r1, g1, b1, data.a1, r2, g2, b2, data.a2);
            return this;
        }
    
        public ParticleBuilder setColor(float r1, float g1, float b1, float r2, float g2, float b2, float a)
        {
            setColor(r1, g1, b1, a, r2, g2, b2, a);
            return this;
        }
    
        public ParticleBuilder setColor(float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2)
        {
            data.r1 = r1;
            data.g1 = g1;
            data.b1 = b1;
            data.a1 = a1;
            data.r2 = r2;
            data.g2 = g2;
            data.b2 = b2;
            data.a2 = a2;
            return this;
        }
    
        public ParticleBuilder setColor(Color c1, Color c2)
        {
            data.r1 = c1.getRed()/255f;
            data.g1 = c1.getGreen()/255f;
            data.b1 = c1.getBlue()/255f;
            data.r2 = c2.getRed()/255f;
            data.g2 = c2.getGreen()/255f;
            data.b2 = c2.getBlue()/255f;
            return this;
        }
        public ParticleBuilder setColorCurveMultiplier(float colorCurveMultiplier)
        {
            data.colorCurveMultiplier = colorCurveMultiplier;
            return this;
        }
        public ParticleBuilder setAlpha(float a)
        {
            setAlpha(a, a);
            return this;
        }
    
        public ParticleBuilder setAlpha(float a1, float a2)
        {
            data.a1 = a1;
            data.a2 = a2;
            return this;
        }
    
        public ParticleBuilder setScale(float scale)
        {
            setScale(scale, scale);
            return this;
        }
    
        public ParticleBuilder setScale(float scale1, float scale2)
        {
            data.scale1 = scale1;
            data.scale2 = scale2;
            return this;
        }
    
        public ParticleBuilder enableGravity()
        {
            data.gravity = true;
            return this;
        }
    
        public ParticleBuilder disableGravity()
        {
            data.gravity = false;
            return this;
        }
        public ParticleBuilder enableNoClip()
        {
            data.noClip = true;
            return this;
        }
    
        public ParticleBuilder disableNoClip()
        {
            data.noClip = false;
            return this;
        }
    
        public ParticleBuilder setSpin(float angularVelocity)
        {
            data.spin = angularVelocity;
            return this;
        }
    
        public ParticleBuilder setLifetime(int lifetime)
        {
            data.lifetime = lifetime;
            return this;
        }
    
        public ParticleBuilder randomVelocity(double maxSpeed)
        {
            randomVelocity(maxSpeed, maxSpeed, maxSpeed);
            return this;
        }
    
        public ParticleBuilder randomVelocity(double maxHSpeed, double maxVSpeed)
        {
            randomVelocity(maxHSpeed, maxVSpeed, maxHSpeed);
            return this;
        }
    
        public ParticleBuilder randomVelocity(double maxXSpeed, double maxYSpeed, double maxZSpeed)
        {
            this.maxXSpeed = maxXSpeed;
            this.maxYSpeed = maxYSpeed;
            this.maxZSpeed = maxZSpeed;
            return this;
        }
    
        public ParticleBuilder addVelocity(double vx, double vy, double vz)
        {
            this.vx += vx;
            this.vy += vy;
            this.vz += vz;
            return this;
        }
    
        public ParticleBuilder setVelocity(double vx, double vy, double vz)
        {
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            return this;
        }
    
        public ParticleBuilder randomOffset(double maxDistance)
        {
            randomOffset(maxDistance, maxDistance, maxDistance);
            return this;
        }
    
        public ParticleBuilder randomOffset(double maxHDist, double maxVDist)
        {
            randomOffset(maxHDist, maxVDist, maxHDist);
            return this;
        }
    
        public ParticleBuilder randomOffset(double maxXDist, double maxYDist, double maxZDist)
        {
            this.maxXDist = maxXDist;
            this.maxYDist = maxYDist;
            this.maxZDist = maxZDist;
            return this;
        }
    
        public ParticleBuilder spawnCircle(World world, double x, double y, double z,double distance, double currentCount, double totalCount)
        {
            double xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
            double theta = (Math.PI * 2) / totalCount;
            double finalAngle = (currentCount / totalCount) + (theta * currentCount);
            double dx2 = (distance * Math.cos(finalAngle));
            double dz2 = (distance * Math.sin(finalAngle));
    
            Vector3d vector2f = new Vector3d(dx2,0,dz2);
            this.vx = vector2f.x * xSpeed;
            this.vz = vector2f.z * zSpeed;
            
            double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2, xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist, zDist = random.nextFloat() * maxZDist;
            this.dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
            this.dy = Math.sin(pitch2) * yDist;
            this.dz = Math.cos(yaw2) * Math.cos(pitch2) * zDist;
            world.addParticle(data, x + dx + dx2, y + dy, z + dz + dz2, vx, ySpeed, vz);
            return this;
        }
        public ParticleBuilder spawn(World world, double x, double y, double z)
        {
            double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
            this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
            this.vy += Math.sin(pitch) * ySpeed;
            this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;
            double yaw2 = random.nextFloat() * Math.PI * 2, pitch2 = random.nextFloat() * Math.PI - Math.PI / 2, xDist = random.nextFloat() * maxXDist, yDist = random.nextFloat() * maxYDist, zDist = random.nextFloat() * maxZDist;
            this.dx = Math.sin(yaw2) * Math.cos(pitch2) * xDist;
            this.dy = Math.sin(pitch2) * yDist;
            this.dz = Math.cos(yaw2) * Math.cos(pitch2) * zDist;
        
            world.addParticle(data, x + dx, y + dy, z + dz, vx, vy, vz);
            return this;
        }

        public ParticleBuilder evenlySpawnAtEdges(World world, BlockPos pos)
        {
            for (Direction direction : Direction.values())
            {
                double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
                this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
                this.vy += Math.sin(pitch) * ySpeed;
                this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;

                Direction.Axis direction$axis = direction.getAxis();
                double d0 = 0.5625D;
                this.dx = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
                this.dy = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
                this.dz = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();

                world.addParticle(data, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, vx, vy, vz);

            }return this;
        }
        public ParticleBuilder evenlySpawnAtEdges(World world, BlockPos pos, Direction... directions)
        {
            for (Direction direction : directions)
            {
                double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
                this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
                this.vy += Math.sin(pitch) * ySpeed;
                this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;

                Direction.Axis direction$axis = direction.getAxis();
                double d0 = 0.5625D;
                this.dx = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
                this.dy = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
                this.dz = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();

                world.addParticle(data, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, vx, vy, vz);

            }return this;
        }
        public ParticleBuilder spawnAtEdges(World world, BlockPos pos)
        {
            Direction direction = Direction.values()[world.rand.nextInt(Direction.values().length)];
            double yaw = random.nextFloat() * Math.PI * 2, pitch = random.nextFloat() * Math.PI - Math.PI / 2, xSpeed = random.nextFloat() * maxXSpeed, ySpeed = random.nextFloat() * maxYSpeed, zSpeed = random.nextFloat() * maxZSpeed;
            this.vx += Math.sin(yaw) * Math.cos(pitch) * xSpeed;
            this.vy += Math.sin(pitch) * ySpeed;
            this.vz += Math.cos(yaw) * Math.cos(pitch) * zSpeed;
        
            Direction.Axis direction$axis = direction.getAxis();
            double d0 = 0.5625D;
            this.dx = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
            this.dy = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
            this.dz = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();
            
            world.addParticle(data, pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz, vx, vy, vz);
            return this;
        }
    
        public ParticleBuilder repeat(World world, double x, double y, double z, int n)
        {
            for (int i = 0; i < n; i++) spawn(world, x, y, z);
            return this;
        }
        public ParticleBuilder repeatEdges(World world, BlockPos pos, int n)
        {
            for (int i = 0; i < n; i++) spawnAtEdges(world, pos);
            return this;
        }
        public ParticleBuilder evenlyRepeatEdges(World world, BlockPos pos, int n)
        {
            for (int i = 0; i < n; i++) evenlySpawnAtEdges(world, pos);
            return this;
        }
        public ParticleBuilder evenlyRepeatEdges(World world, BlockPos pos, int n, Direction... directions)
        {
            for (int i = 0; i < n; i++) evenlySpawnAtEdges(world, pos, directions);
            return this;
        }
        public ParticleBuilder repeatCircle(World world, double x, double y, double z, double distance, int times)
        {
            for (int i = 0; i < times; i++) spawnCircle(world, x, y, z, distance,i, times);
            return this;
        }
    }
    
    public static ParticleBuilder create(ParticleType<?> type)
    {
        return new ParticleBuilder(type);
    }
    
    public static ParticleBuilder create(RegistryObject<?> type)
    {
        return new ParticleBuilder((ParticleType<?>) type.get());
    }
}